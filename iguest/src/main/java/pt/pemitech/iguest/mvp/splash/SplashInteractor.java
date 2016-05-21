package pt.pemitech.iguest.mvp.splash;

import java.util.List;

import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.pojo.Event;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao on 19/05/16.
 */
public class SplashInteractor implements SplashContract.Interactor {
    @Override
    public void fetchEvents(final PresenterCallback listener) {
        RestClient.get().getEvents(new Callback<List<Event>>() {
            @Override
            public void success(List<Event> events, Response response) {
                if (listener != null) {
                    listener.onEventsFetched(events);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    switch(error.getKind()) {
                        case NETWORK:
                            listener.onEventsFetchError("Network not available!");
                            break;
                        case CONVERSION:
                            // Handled the same as HTTP errors
                        case HTTP:
                            listener.onEventsFetchError("Server Error! Retry later.");
                            break;
                        case UNEXPECTED:
                            listener.onEventsFetchError("Unexpected Error.");
                            break;
                    }
                }
            }
        });
    }
}
