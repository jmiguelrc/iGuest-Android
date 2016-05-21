package pt.pemitech.iguest.mvp.add_guest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;
import pt.pemitech.iguest.pojo.Guest;

public class AddGuestView extends DialogFragment implements IAddGuestMvp.View {
    private EditText mEtName, mEtPhone;
    private String mEventId;
    private IAddGuestMvp.Presenter mPresenter;

    public static AddGuestView newInstance(String eventId) {
        AddGuestView f = new AddGuestView();

        Bundle args = new Bundle();
        args.putString("id", eventId);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventId = getArguments().getString("id");

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Tracker t = ((IGuestApplication) getActivity().getApplication()).getTracker(
        );
        t.setScreenName("AddGuestView");
        t.send(new HitBuilders.AppViewBuilder().build());

        t.send(new HitBuilders.EventBuilder()
                .setCategory("Opened View")
                .setAction("Add Guests View")
                .setLabel(mEventId)
                .build());

        mPresenter = new AddGuestPresenter(this);

        getDialog().setTitle(R.string.add_guest_title);
        View v = inflater.inflate(R.layout.fragment_add_guest, container, false);

        mEtName = (EditText)v.findViewById(R.id.etName);
        mEtPhone = (EditText)v.findViewById(R.id.etPhone);
        Button btAddGuest = (Button)v.findViewById(R.id.btAdd);
        btAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Guest guest = new Guest();
                guest.setName(mEtName.getText().toString());
                guest.setPhone(mEtPhone.getText().toString());
                mPresenter.addGuest(mEventId, guest);
            }
        });

        return v;
    }

    @Override
    public void showGuestAddedMessageAndDismiss() {
        Toast.makeText(getActivity(), "Guest added", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showProgress() {
        // Do nothing for now
    }

    @Override
    public void hideProgress() {
        // Do nothing for now
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}