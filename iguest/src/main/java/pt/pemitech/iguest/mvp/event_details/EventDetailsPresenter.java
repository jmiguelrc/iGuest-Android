package pt.pemitech.iguest.mvp.event_details;

import java.lang.ref.WeakReference;

import pt.pemitech.iguest.pojo.Club;
import pt.pemitech.iguest.pojo.Event;

/**
 * Created by joao on 20/05/16.
 */
public class EventDetailsPresenter implements IEventDetailsMvp.Presenter, IEventDetailsMvp.Interactor.PresenterCallback {
    private WeakReference<IEventDetailsMvp.View> mView;
    private IEventDetailsMvp.Interactor mInteractor;

    public EventDetailsPresenter(IEventDetailsMvp.View view) {
        mInteractor = new EventDetailsInteractor();
        mView = new WeakReference<>(view);
    }

    @Override
    public void getEventDescription(Event event) {
        mInteractor.fetchEventDescription(event, this);
    }

    @Override
    public void getClub(Event event) {
        mInteractor.fetchClub(event, this);
    }

    @Override
    public void onFetchedEventDescription(String description) {
        if (mView.get() != null && description != null) {
            mView.get().showEventDescription(description);
        }
    }

    @Override
    public void onFetchEventDescriptionError(String error) {
        // Do nothing
    }

    @Override
    public void onFetchedClub(Club club) {
        if (mView.get() != null && club != null) {
            mView.get().showClubInfo(club);
        }
    }

    @Override
    public void onFetchClubError(String error) {
        // Do nothing
    }
}
