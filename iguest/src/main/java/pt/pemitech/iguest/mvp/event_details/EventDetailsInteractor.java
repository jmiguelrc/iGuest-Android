package pt.pemitech.iguest.mvp.event_details;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.pojo.Club;
import pt.pemitech.iguest.pojo.Event;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by joao on 20/05/16.
 */
public class EventDetailsInteractor implements IEventDetailsMvp.Interactor {
    @Override
    public void fetchEventDescription(Event event, final PresenterCallback listener) {
        RestClient.get().getEventDescription(event.getId(), new Callback<Response>() {
            @Override
            public void success(Response response, Response ignored) {
                TypedInput body = response.getBody();
                if (body == null) {
                    if (listener != null) {
                        listener.onFetchEventDescriptionError("Response body is null");
                    }
                    return;
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
                    StringBuilder out = new StringBuilder();
                    String newLine = System.getProperty("line.separator");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                        out.append(newLine);
                    }
                    if (listener != null) {
                        listener.onFetchedEventDescription(out.toString());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onFetchEventDescriptionError("IO Exception when reading description.");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    switch (error.getKind()) {
                        case NETWORK:
                            listener.onFetchEventDescriptionError("Network not available!");
                            break;
                        case CONVERSION:
                            // Handled the same as HTTP errors
                        case HTTP:
                            listener.onFetchEventDescriptionError("Server Error! Retry later.");
                            break;
                        case UNEXPECTED:
                            listener.onFetchEventDescriptionError("Unexpected Error.");
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void fetchClub(Event event, final PresenterCallback listener) {
        RestClient.get().getClub(event.getClubId(), new Callback<Club>() {
            @Override
            public void success(Club club, Response response) {
                if (listener != null) {
                    listener.onFetchedClub(club);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null) {
                    switch (error.getKind()) {
                        case NETWORK:
                            listener.onFetchClubError("Network not available!");
                            break;
                        case CONVERSION:
                            // Handled the same as HTTP errors
                        case HTTP:
                            listener.onFetchClubError("Server Error! Retry later.");
                            break;
                        case UNEXPECTED:
                            listener.onFetchClubError("Unexpected Error.");
                            break;
                    }
                }
            }
        });
    }
}
