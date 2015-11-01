package pt.pemitech.iguest.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import pt.pemitech.iguest.R;
import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.model.Event;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RestClient.get().getEvents(new Callback<List<Event>>() {
            @Override
            public void success(List<Event> events, Response response) {
                if (events.isEmpty()) {
                    showErrorDialog(getString(R.string.parties), getString(R.string.no_parties_available));
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("events", (Serializable) events);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showErrorDialog(getString(R.string.internet_error), getString(R.string.unavailable_network));
            }
        });
    }

    private void showErrorDialog(String title, String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish(); // Finish application, no point in showing nothing to the user
            }
        });
        alertDialog.show();
    }
}
