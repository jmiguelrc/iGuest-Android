package pt.pemitech.iguest.mvp.list_events;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;
import pt.pemitech.iguest.pojo.Event;

@SuppressWarnings("FieldCanBeLocal")
public class ListEventsView extends Fragment implements IListEventsMvp.View {

    private ListView mListView;
    private EventsAdapter mEventsAdapter;
    private List<Event> mEvents;
    private IListEventsMvp.Presenter mPresenter;

    public ListEventsView() {
        mEvents = null;
    }

    public ListEventsView(List<Event> events) {
        mEvents = events;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ListEventsPresenter(this);

        //Tracking View
        Tracker t = ( (IGuestApplication) getActivity().getApplication()).getTracker(
        );
        t.setScreenName("ListEventsView");
        t.send(new HitBuilders.AppViewBuilder().build());

        mEventsAdapter = new EventsAdapter(getActivity(), mEvents);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.setAdapter(mEventsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_load:
                Log.d("ListEventsView", "Fetching Events");
                mPresenter.refreshEvents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateEvents(List<Event> events) {
        mEvents = events;

        if(getActivity() != null && mListView != null){
            mListView.setAdapter(new EventsAdapter(getActivity(), mEvents));
            Toast.makeText(getActivity(), "Refreshed events", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}