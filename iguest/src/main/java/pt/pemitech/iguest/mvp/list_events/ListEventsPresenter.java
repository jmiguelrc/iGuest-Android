package pt.pemitech.iguest.mvp.list_events;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.List;

import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 19/05/16.
 */
public class ListEventsPresenter implements IListEventsMvp.Presenter, IListEventsMvp.Interactor.PresenterCallback {
    private WeakReference<IListEventsMvp.View> mView;
    private IListEventsMvp.Interactor mInteractor;

    public ListEventsPresenter(@NonNull IListEventsMvp.View view) {
        mView = new WeakReference<>(view);
        mInteractor = new ListEventsInteractor();
    }

    @Override
    public void onEventsFetched(List<Event> events) {
        if (mView.get() != null) {
            mView.get().updateEvents(events);
        }
    }

    @Override
    public void onEventsFetchError(String error) {
        if (mView.get() != null) {
            mView.get().showErrorMessage(error);
        }
    }

    @Override
    public void refreshEvents() {
        mInteractor.fetchEvents(this);
    }
}