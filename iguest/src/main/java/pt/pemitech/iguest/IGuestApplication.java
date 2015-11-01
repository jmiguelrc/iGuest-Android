package pt.pemitech.iguest;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

public class IGuestApplication extends Application {
    private final HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public enum TrackerName {
        APP_TRACKER // Tracker used only in this app.
    }

    public synchronized Tracker getTracker() {
        if (!mTrackers.containsKey(TrackerName.APP_TRACKER)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(TrackerName.APP_TRACKER, t);

        }
        return mTrackers.get(TrackerName.APP_TRACKER);
    }
}
