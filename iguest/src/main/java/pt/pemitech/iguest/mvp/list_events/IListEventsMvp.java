package pt.pemitech.iguest.mvp.list_events;

import java.util.List;

import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 19/05/16.
 */
public interface IListEventsMvp {
    interface View {
        void updateEvents(List<Event> events);
        void showErrorMessage(String message);
    }

    interface Presenter {
        void refreshEvents();
    }

    interface Interactor {
        interface PresenterCallback {
            void onEventsFetched(List<Event> events);
            void onEventsFetchError(String error);
        }

        void fetchEvents(PresenterCallback listener);
    }
}
