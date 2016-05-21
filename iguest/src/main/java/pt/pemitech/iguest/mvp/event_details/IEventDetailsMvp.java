package pt.pemitech.iguest.mvp.event_details;

import pt.pemitech.iguest.pojo.Club;
import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 20/05/16.
 */
public interface IEventDetailsMvp {
    interface View {
        void showEventDescription(String description);
        void showClubInfo(Club club);
        void showErrorMessage(String error);
    }

    interface Presenter {
        void getEventDescription(Event event);
        void getClub(Event event);
    }

    interface Interactor {
        interface PresenterCallback {
            void onFetchedEventDescription(String description);
            void onFetchEventDescriptionError(String error);
            void onFetchedClub(Club club);
            void onFetchClubError(String error);
        }

        void fetchEventDescription(Event event, PresenterCallback listener);
        void fetchClub(Event event, PresenterCallback listener);
    }
}