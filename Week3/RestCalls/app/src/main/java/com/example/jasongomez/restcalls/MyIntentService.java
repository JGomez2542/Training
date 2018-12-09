package com.example.jasongomez.restcalls;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");

        String url = intent.getStringExtra(Constants.KEY.URL);
        Intent newIntent = new Intent();
        Scanner scanner = null;

        try {
            URL connectionUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) connectionUrl.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            scanner = new Scanner(inputStream);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            int statusCode = httpURLConnection.getResponseCode();
            String statusMessage = httpURLConnection.getResponseMessage();
            newIntent.setAction(Constants.NATIVE_RECEIVER_ACTION);
            newIntent.putExtra(Constants.KEY.CODE, statusCode);
            newIntent.putExtra(Constants.KEY.MESSAGE, statusMessage);
            newIntent.putExtra(Constants.KEY.RESPONSE, sb.toString());
            sendBroadcast(newIntent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
