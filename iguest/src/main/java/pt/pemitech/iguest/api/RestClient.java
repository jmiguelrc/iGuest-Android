package pt.pemitech.iguest.api;

import retrofit.RestAdapter;

/**
 * Created by joao on 24/10/15.
 */
@SuppressWarnings("DefaultFileTemplate")
public class RestClient {

    private static RestApi restClientInstance = null;
    public static final String SERVER = "https://stark-escarpment-6928.herokuapp.com";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public synchronized static RestApi get() {
        if (restClientInstance == null)
            setupRestClient();
        return restClientInstance;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(SERVER)
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        restClientInstance = restAdapter.create(RestApi.class);
    }
}