package pt.pemitech.iguest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;
import pt.pemitech.iguest.adapters.EventsAdapter;
import pt.pemitech.iguest.model.Event;

@SuppressWarnings("FieldCanBeLocal")
public class ListFragment extends Fragment {

    private ListView mListView;
    private EventsAdapter mEventsAdapter;
    private List<Event> mEvents;

    public ListFragment() {
        mEvents = null;
    }

    public ListFragment(List<Event> events) {
        mEvents = events;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Tracking View
        Tracker t = ( (IGuestApplication) getActivity().getApplication()).getTracker(
        );
        t.setScreenName("ListFragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        mEventsAdapter = new EventsAdapter(getActivity(), mEvents);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.setAdapter(mEventsAdapter);
    }

    public void updateAdapter(List<Event> refreshedEvents){
        mEvents = refreshedEvents;

        if(getActivity() != null && mListView != null){
            mListView.setAdapter(new EventsAdapter(getActivity(), mEvents));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

}
