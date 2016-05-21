package pt.pemitech.iguest.mvp.splash;

import java.util.List;

import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 19/05/16.
 */
public interface SplashContract {
    interface View {
        void eventsReceived(List<Event> events);
        void showErrorDialogAndQuit(String title, String error);
    }

    interface Presenter {
        void getEvents();
    }

    interface Interactor {
        interface PresenterCallback {
            void onEventsFetched(List<Event> events);
            void onEventsFetchError(String error);
        }

        void fetchEvents(PresenterCallback listener);
    }
}
