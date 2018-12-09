package com.example.jasongomez.restcalls.data.remote;

import com.example.jasongomez.restcalls.Constants;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RemoteServiceHelper {

    private static RemoteServiceHelper INSTANCE;
    private static final String ZIP = "94040";
    private static final String APPID = "b1b15e88fa797225412429c1c50c122a1";
    private OkHttpClient okHttpClient;


    private RemoteServiceHelper() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

    }

    public static RemoteServiceHelper getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new RemoteServiceHelper();
        }
        return INSTANCE;
    }

    public Call<ResponseBody> getWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.WEATHER_BASE_URL)
                .build();

        RemoteService service = retrofit.create(RemoteService.class);
        return service.getWeatherData(ZIP, APPID);
    }
}
