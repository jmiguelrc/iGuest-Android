package pt.pemitech.iguest.mvp.add_guest;

import pt.pemitech.iguest.pojo.Guest;

/**
 * Created by joao on 20/05/16.
 */
public interface IAddGuestMvp {
    interface View {
        void showGuestAddedMessageAndDismiss();
        void showProgress();
        void hideProgress();
        void showErrorMessage(String error);
    }

    interface Presenter {
        boolean validateGuestDetails(Guest guest);
        void addGuest(String eventId, Guest guest);
    }

    interface Interactor {
        interface PresenterCallback {
            void onGuestAdded();
            void onGuestAddError(String error);
        }

        void addGuest(String eventId, Guest guest, PresenterCallback listener);
    }
}
