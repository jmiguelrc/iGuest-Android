package pt.pemitech.iguest.mvp.add_guest;

import java.lang.ref.WeakReference;

import pt.pemitech.iguest.pojo.Guest;

/**
 * Created by joao on 20/05/16.
 */
public class AddGuestPresenter implements IAddGuestMvp.Presenter, IAddGuestMvp.Interactor.PresenterCallback {
    private WeakReference<IAddGuestMvp.View> mView;
    private IAddGuestMvp.Interactor mInteractor;

    public AddGuestPresenter(IAddGuestMvp.View view) {
        mView = new WeakReference<>(view);
        mInteractor = new AddGuestInteractor();
    }

    @Override
    public boolean validateGuestDetails(Guest guest) {
        // TODO implement this
        return true;
    }

    @Override
    public void addGuest(String eventId, Guest guest) {
        if (validateGuestDetails(guest) && mView.get() != null) {
            mInteractor.addGuest(eventId, guest, this);
            mView.get().showProgress();
        }
    }

    @Override
    public void onGuestAdded() {
        if (mView.get() != null) {
            mView.get().hideProgress();
            mView.get().showGuestAddedMessageAndDismiss();
        }
    }

    @Override
    public void onGuestAddError(String error) {
        mView.get().showErrorMessage(error);
    }
}
