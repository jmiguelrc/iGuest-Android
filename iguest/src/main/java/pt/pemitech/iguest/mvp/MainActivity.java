package pt.pemitech.iguest.mvp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import pt.pemitech.iguest.R;
import pt.pemitech.iguest.mvp.add_guest.AddGuestView;
import pt.pemitech.iguest.mvp.event_details.EventDetailsView;
import pt.pemitech.iguest.mvp.full_event_art.EventArtFullActivity;
import pt.pemitech.iguest.mvp.list_events.ListEventsView;
import pt.pemitech.iguest.pojo.Event;

public class MainActivity extends Activity {
    private EventDetailsView mEventDetailsView;
    @SuppressWarnings("FieldCanBeLocal")
    private List<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldable_details);

        //Fade transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        //noinspection unchecked
        mEvents = (List<Event>) getIntent().getSerializableExtra("events");

        //FRAGMENT HANDLING
        String listFragmentTag = ListEventsView.class.getSimpleName();
        String detailsFragmentTag = EventDetailsView.class.getSimpleName();

        ListEventsView listEventsView = (ListEventsView) getFragmentManager().findFragmentByTag(listFragmentTag);
        EventDetailsView eventDetailsView = (EventDetailsView) getFragmentManager().findFragmentByTag(detailsFragmentTag);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (listEventsView == null) {
            listEventsView = new ListEventsView(mEvents);
            transaction.add(R.id.fragment_container, listEventsView, listFragmentTag);
        }
        if (eventDetailsView == null) {
            eventDetailsView = new EventDetailsView();
            transaction.add(R.id.fragment_container, eventDetailsView, detailsFragmentTag);
        }
        transaction.commit();

        mEventDetailsView = eventDetailsView;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        } else if (mEventDetailsView == null || !mEventDetailsView.onActivityBackPressed()) {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Event event) {
        mEventDetailsView.showDetails(coverView, event);
    }

    public void openEventArtFull(Event event) {
        Intent i = new Intent(getApplicationContext(), EventArtFullActivity.class);
        i.putExtra("imageUrl", event.getImage());
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_load:
                return false;
            case R.id.btAddGuest:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = AddGuestView.newInstance(EventDetailsView.mEvent.getId());
                newFragment.show(ft, "dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
