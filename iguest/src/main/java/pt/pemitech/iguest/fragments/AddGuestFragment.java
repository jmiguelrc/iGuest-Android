package pt.pemitech.iguest.fragments;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;

public class AddGuestFragment extends DialogFragment {
    public interface OnAddGuestCallback {
        void addGuestCallback(String name, String phone, String event_id);
    }

    private EditText mEtName, mEtPhone;
    private OnAddGuestCallback mCallback;
    private String mEventId;

    public static AddGuestFragment newInstance(String eventId) {
        AddGuestFragment f = new AddGuestFragment();

        Bundle args = new Bundle();
        args.putString("id", eventId);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAddGuestCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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
        t.setScreenName("AddGuestFragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        t.send(new HitBuilders.EventBuilder()
                .setCategory("Opened View")
                .setAction("Add Guests View")
                .setLabel(mEventId)
                .build());

        getDialog().setTitle(R.string.add_guest_title);
        View v = inflater.inflate(R.layout.fragment_add_guest, container, false);

        mEtName = (EditText)v.findViewById(R.id.etName);
        mEtPhone = (EditText)v.findViewById(R.id.etPhone);
        Button btAddGuest = (Button)v.findViewById(R.id.btAdd);
        btAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.addGuestCallback(mEtName.getText().toString(), mEtPhone.getText().toString(), mEventId);
                dismiss();
            }
        });

        return v;
    }


}
