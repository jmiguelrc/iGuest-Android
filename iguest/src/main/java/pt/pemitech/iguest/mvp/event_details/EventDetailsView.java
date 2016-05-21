package pt.pemitech.iguest.mvp.event_details;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shadow.GlanceFoldShadow;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;
import pt.pemitech.iguest.mvp.MainActivity;
import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.pojo.Club;
import pt.pemitech.iguest.pojo.Event;

public class EventDetailsView extends Fragment implements IEventDetailsMvp.View {

    private UnfoldableView mUnfoldableView;
    private TextView mTVDetails;
    private Menu mMenu;
    private View mDetailsLayout;
    private ScrollView mScrollView;
    private ImageView mImageView;
    private TextView mTitleView;
    public static Event mEvent;
    private TextView mDescriptionView;
    private Club mClub;
    private IEventDetailsMvp.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnfoldableView = (UnfoldableView) view.findViewById(R.id.unfoldable_view);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_view);
        mTVDetails = (TextView) view.findViewById(R.id.tvDetails);
        mTVDetails.setBackgroundColor(Color.TRANSPARENT);

        mDetailsLayout = view.findViewById(R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.OnFoldingListener() {
            @Override
            public void onFoldProgress(UnfoldableView unfoldableView, float progress) {
                // NO-OP
            }

            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                //mTouchInterceptorView.setClickable(true);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                // mTouchInterceptorView.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                //mTouchInterceptorView.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                //mTouchInterceptorView.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
                if (mMenu != null) {
                    mMenu.findItem(R.id.btAddGuest).setVisible(false);
                    mMenu.findItem(R.id.btShare).setVisible(false);
                    mMenu.findItem(R.id.btMaps).setVisible(false);
                    mMenu.findItem(R.id.menu_load).setVisible(true);
                }
            }
        });

        @SuppressWarnings({"deprecation", "ConstantConditions"}) Bitmap glance = ((BitmapDrawable) getResources().getDrawable(R.drawable.unfold_glance)).getBitmap();
        mUnfoldableView.setFoldShader(new GlanceFoldShadow(getActivity(), glance));

        mImageView = (ImageView) view.findViewById(R.id.details_image);
        mTitleView = (TextView) view.findViewById(R.id.details_title);
        mDescriptionView = (TextView) view.findViewById(R.id.details_text);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.openEventArtFull(mEvent);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mMenu == null) {
            mMenu = menu;
            inflater.inflate(R.menu.details, menu);

            mMenu.findItem(R.id.btAddGuest).setVisible(false);
            mMenu.findItem(R.id.btShare).setVisible(false);
            mMenu.findItem(R.id.btMaps).setVisible(false);
            mMenu.findItem(R.id.menu_load).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.btAddGuest):

                return true;
            case (R.id.btMaps):
                String uriBegin = "geo:0,0?q=";
                String encodedQuery = Uri.encode(mClub.getAddress());
                String uriString = uriBegin + encodedQuery + "?z=16";
                Uri uri = Uri.parse(uriString);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);

                return true;
            case (R.id.btShare):
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        getActivity().getResources().getString(R.string.share_first_part) +
                                mEvent.getTitle() + getActivity().getResources().getString(R.string.share_middle_part)
                                + mEvent.getClubName()
                );
                startActivity(Intent.createChooser(sharingIntent,
                        getActivity().getResources().getString(R.string.share_with)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onActivityBackPressed() {
        if (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding()) {
            mUnfoldableView.foldBack();
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void showDetails(View coverView, Event event) {
        //Tracking View
        Tracker t = ((IGuestApplication) getActivity().getApplication()).getTracker(
        );
        t.setScreenName("EventDetailsView");
        t.send(new HitBuilders.AppViewBuilder().build());

        // Build and send an Event
        t.send(new HitBuilders.EventBuilder()
                .setCategory("Opened View")
                .setAction("Details View")
                .setLabel(event.getId())
                .build());

        mEvent = event;

        mPresenter = new EventDetailsPresenter(this);
        mPresenter.getClub(mEvent);
        mPresenter.getEventDescription(mEvent);

        Picasso.with(getActivity())
                .load(RestClient.SERVER + "/" + event.getImage())
                .resize(500, 200)
                .placeholder(R.drawable.load_placeholder)
                .into(mImageView);
        mTitleView.setText(event.getTitle());

        try {
            @SuppressLint("SimpleDateFormat") DateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            @SuppressLint("SimpleDateFormat") DateFormat toDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date eventDate = originalDateFormat.parse(mEvent.getDate());

            mDescriptionView.setText(event.getClubName() + "\n" + toDateFormat.format(eventDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mTVDetails.setText("");

        mDetailsLayout.setVisibility(View.VISIBLE);
        mUnfoldableView.unfold(coverView, mDetailsLayout);

        if (mMenu != null) {
            mMenu.findItem(R.id.btAddGuest).setVisible(true);
            mMenu.findItem(R.id.btShare).setVisible(true);
            mMenu.findItem(R.id.menu_load).setVisible(false);
        }
    }

    @Override
    public void showEventDescription(String description) {
        mTVDetails.setText(description);
    }

    @Override
    public void showClubInfo(Club club) {
        mClub = club;
        if (mMenu != null) {
            mMenu.findItem(R.id.btMaps).setVisible(true);
        }
    }

    @Override
    public void showErrorMessage(String error) {
        // Do nothing
    }
}
