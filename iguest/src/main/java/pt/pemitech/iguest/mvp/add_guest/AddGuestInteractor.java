package pt.pemitech.iguest.mvp.add_guest;

import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.pojo.Guest;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao on 20/05/16.
 */
public class AddGuestInteractor implements IAddGuestMvp.Interactor {
    @Override
    public void addGuest(String eventId, Guest guest, final PresenterCallback listener) {
        RestClient.get().addGuest(eventId, guest, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                if (listener != null) {
                    listener.onGuestAdded();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    switch(error.getKind()) {
                        case NETWORK:
                            listener.onGuestAddError("Network not available!");
                            break;
                        case CONVERSION:
                            // Handled the same as HTTP errors
                        case HTTP:
                            listener.onGuestAddError("Server Error! Retry later.");
                            break;
                        case UNEXPECTED:
                            listener.onGuestAddError("Unexpected Error.");
                            break;
                    }
                }
            }
        });
    }
}
