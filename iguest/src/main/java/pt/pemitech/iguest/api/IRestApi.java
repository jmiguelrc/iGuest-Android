package pt.pemitech.iguest.api;

import java.util.List;

import pt.pemitech.iguest.pojo.Club;
import pt.pemitech.iguest.pojo.Event;
import pt.pemitech.iguest.pojo.Guest;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by joao on 24/10/15.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface IRestApi {
    @GET("/v1/events")
    void getEvents(Callback<List<Event>> response);

    @GET("/v1/event/{event_id}/description")
    void getEventDescription(@Path("event_id") String eventId, Callback<Response> response);

    @POST("/v1/event/{event_id}/add_guest")
    void addGuest(@Path("event_id") String eventId, @Body Guest guest, Callback<Response> response);

    @GET("/v1/club/{club_id}")
    void getClub(@Path("club_id") String clubId, Callback<Club> response);
}