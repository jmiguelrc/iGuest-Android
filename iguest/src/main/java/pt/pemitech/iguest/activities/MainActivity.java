package pt.pemitech.iguest.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pt.pemitech.iguest.R;
import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.fragments.AddGuestFragment;
import pt.pemitech.iguest.fragments.DetailsFragment;
import pt.pemitech.iguest.fragments.ListFragment;
import pt.pemitech.iguest.model.Event;
import pt.pemitech.iguest.model.Guest;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements AddGuestFragment.OnAddGuestCallback {
    private DetailsFragment mDetailsFragment;
    private ListFragment mListFragment;
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
        String listFragmentTag = ListFragment.class.getSimpleName();
        String detailsFragmentTag = DetailsFragment.class.getSimpleName();

        ListFragment listFragment = (ListFragment) getFragmentManager().findFragmentByTag(listFragmentTag);
        DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentByTag(detailsFragmentTag);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (listFragment == null) {
            listFragment = new ListFragment(mEvents);
            transaction.add(R.id.fragment_container, listFragment, listFragmentTag);
        }
        if (detailsFragment == null) {
            detailsFragment = new DetailsFragment();
            transaction.add(R.id.fragment_container, detailsFragment, detailsFragmentTag);
        }
        transaction.commit();

        mListFragment = listFragment;
        mDetailsFragment = detailsFragment;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        } else if (mDetailsFragment == null || !mDetailsFragment.onActivityBackPressed()) {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Event event) {
        mDetailsFragment.showDetails(coverView, event);
    }

    public void openEventArtFull(Event event) {
        Intent i = new Intent(getApplicationContext(), EventArtFull.class);
        i.putExtra("imageUrl", event.getImage());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_load:
                RestClient.get().getEvents(new Callback<List<Event>>() {
                    @Override
                    public void success(List<Event> events, Response response) {
                        mListFragment.updateAdapter(events);
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            case R.id.btAddGuest:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = AddGuestFragment.newInstance(DetailsFragment.mEvent.getId());
                newFragment.show(ft, "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addGuestCallback(String name, String phone, String eventId) {
        Guest guest = new Guest();
        guest.setName(name);
        guest.setPhone(phone);

        RestClient.get().addGuest(eventId, guest, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(MainActivity.this, getString(R.string.add_success_title), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK)
                    Toast.makeText(MainActivity.this, getString(R.string.unavailable_network), Toast.LENGTH_LONG).show();
                else if (error.getKind() != RetrofitError.Kind.HTTP)
                    Toast.makeText(MainActivity.this, getString(R.string.unavailable_network), Toast.LENGTH_LONG).show();
                else if (error.getResponse().getStatus() == 400) {
                    Toast.makeText(MainActivity.this, getString(R.string.incorrect_details), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
