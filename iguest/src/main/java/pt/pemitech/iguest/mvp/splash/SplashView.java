package pt.pemitech.iguest.mvp.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import pt.pemitech.iguest.R;
import pt.pemitech.iguest.mvp.MainActivity;
import pt.pemitech.iguest.pojo.Event;

public class SplashView extends Activity implements SplashContract.View {
    private SplashContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPresenter = new SplashPresenter(this);
        mPresenter.getEvents();
    }

    @Override
    public void showErrorDialogAndQuit(String title, String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashView.this);
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

    @Override
    public void eventsReceived(List<Event> events) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("events", (Serializable) events);
        startActivity(intent);
        finish();
    }
}
