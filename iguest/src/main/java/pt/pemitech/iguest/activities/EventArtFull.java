package pt.pemitech.iguest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import pt.pemitech.iguest.IGuestApplication;
import pt.pemitech.iguest.R;
import pt.pemitech.iguest.api.RestClient;

public class EventArtFull extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_art_full);

        Tracker t = ((IGuestApplication) getApplication()).getTracker(
        );
        t.setScreenName("EventArtFull");
        t.send(new HitBuilders.AppViewBuilder().build());

        final View contentView = findViewById(R.id.fullscreen_content);
        Intent i = getIntent();
        String url = i.getStringExtra("imageUrl");

        Picasso.with(this)
                .load(RestClient.SERVER + "/" + url)
                .placeholder(R.drawable.load_placeholder)
                .resize(2000, 800)
                .rotate(90)
                .into((ImageView) contentView);
        
        ((ImageViewTouch) contentView).setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
    }
}
