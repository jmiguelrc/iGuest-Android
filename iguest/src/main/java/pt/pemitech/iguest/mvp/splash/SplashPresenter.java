package pt.pemitech.iguest.mvp.splash;

import java.lang.ref.WeakReference;
import java.util.List;

import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 19/05/16.
 */
public class SplashPresenter implements SplashContract.Presenter, SplashContract.Interactor.PresenterCallback {
    private WeakReference<SplashContract.View> mView;
    private SplashContract.Interactor mInteractor;

    public SplashPresenter(SplashContract.View view) {
        mView = new WeakReference<>(view);
        mInteractor = new SplashInteractor();
    }

    @Override
    public void getEvents() {
        mInteractor.fetchEvents(this);
    }

    @Override
    public void onEventsFetched(List<Event> events) {
        if (mView.get() != null) {
            if (events.isEmpty()) {
                mView.get().showErrorDialogAndQuit("Events", "No Events Available");
            } else {
                mView.get().eventsReceived(events);
            }
        }
    }

    @Override
    public void onEventsFetchError(String error) {
        if (mView.get() != null) {
            mView.get().showErrorDialogAndQuit("Error", error);
        }
    }
}
