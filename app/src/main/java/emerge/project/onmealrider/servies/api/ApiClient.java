package emerge.project.onmealrider.servies.api;


import java.util.concurrent.TimeUnit;


import emerge.project.onmealrider.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Himanshu on 8/24/2017.
 */

public class ApiClient {

    public static final String baseUrl = BuildConfig.API_BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


   private static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);
        return okhttpClientBuilder.build();
    }


}
