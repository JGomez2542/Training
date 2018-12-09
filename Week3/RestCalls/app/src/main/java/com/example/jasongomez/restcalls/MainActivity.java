package com.example.jasongomez.restcalls;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jasongomez.restcalls.data.remote.RemoteServiceHelper;
import com.example.jasongomez.restcalls.models.WeatherData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NativeReceiver nativeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nativeReceiver = new NativeReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.NATIVE_RECEIVER_ACTION);
        registerReceiver(nativeReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(nativeReceiver);
    }

    public void makeCall(View view) {

        OkHttpClient okHttpClient = new OkHttpClient();

        switch (view.getId()) {

            case R.id.btnNativeHttp:
                Intent intent = new Intent(this, MyIntentService.class);
                intent.putExtra(Constants.KEY.URL, Constants.INTENT_SERVICE_BASE_URL);
                startService(intent);
                break;

            case R.id.btnOkHttpSync:
                Request syncRequest = new Request.Builder()
                        .url(Constants.PERSON_BASE_URL)
                        .build();

                new Thread(() -> {
                    try {
                        Response response = okHttpClient.newCall(syncRequest).execute();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        runOnUiThread(() -> {
                            try {
                                Toast.makeText(MainActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;

            case R.id.btnOkHttpAsync:
                Request asyncRequest = new Request.Builder()
                        .url(Constants.PERSON_BASE_URL)
                        .build();
                okHttpClient.newCall(asyncRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(() -> {
                                    try {
                                        Gson gson = new Gson();
                                        String personJson = response.body().string();
                                        Person person = gson.fromJson(personJson, Person.class);
                                        Log.d(TAG, "onResponse: " + person.toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                                }
                        );
                    }
                });
                break;

            case R.id.btnRetrofitSync:
                RemoteServiceHelper remoteServiceHelper = RemoteServiceHelper.getInstance();
                Gson gson = new Gson();

                new Thread(() -> {
                    try {
                        retrofit2.Response<ResponseBody> response = remoteServiceHelper.getWeatherData().execute();
                        String json = response.body().string();
                        WeatherData data = gson.fromJson(json, WeatherData.class);
                        Log.d(TAG, "City name: " + data.getCity().getName()
                                + " " + "City Population: " + data.getCity().getPopulation());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;

            case R.id.btnRetrofitAsync:


        }
    }
}
