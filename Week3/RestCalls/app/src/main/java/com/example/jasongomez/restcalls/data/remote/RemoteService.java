package com.example.jasongomez.restcalls.data.remote;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteService {

    @GET("data/2.5/forecast")
    Call<ResponseBody> getWeatherData(@Query("zip") String zip, @Query("appid") String appId);

}
