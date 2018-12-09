package com.example.jasongomez.savinginstancestate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText etName, personName, personGender;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding views
        etName = findViewById(R.id.etName);
        tvName = findViewById(R.id.tvName);
        personName = findViewById(R.id.etPersonName);
        personGender = findViewById(R.id.etPersonGender);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String data = tvName.getText().toString();
        outState.putString("data", data);

        Log.d(TAG, "onSaveInstanceState: " + tvName.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tvName.setText(savedInstanceState.getString("data"));
        Log.d(TAG, "onRestoreInstanceState: " + tvName.getText().toString());
    }

    public void doSomething(View view) throws NoSuchAlgorithmException {
        switch (view.getId()) {
            case R.id.btnChangeText:
                String name = etName.getText().toString();
                tvName.setText(name);
                break;

            case R.id.btnGoToSecond:
                List<Person> personList = new ArrayList<>();
                personList.add(new Person(personName.getText().toString(),
                        personGender.getText().toString()));
                personList.add(new Person(getMessageDigest(personName.getText().toString()),
                        getMessageDigest(personGender.getText().toString())));
                Intent intent = new Intent(this, SecondActivity.class);
                intent.setAction("goToSecond");
                intent.putParcelableArrayListExtra("person", (ArrayList<? extends Parcelable>) personList);
                startActivity(intent);
                break;

            case R.id.btnGoToSecondForResult:
                Intent forResultIntent = new Intent(this, SecondActivity.class);
                Person person = new Person(personName.getText().toString(), personGender.getText().toString());
                forResultIntent.setAction("goToSecondForResult");
                forResultIntent.putExtra("person", person);
                startActivityForResult(forResultIntent, 2);
                break;

            case R.id.btnSaveToSharedPrefs:
                SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", personName.getText().toString());
                editor.putString("gender", personGender.getText().toString());
                editor.commit();
                Intent intent1 = new Intent(this, SecondActivity.class);
                intent1.setAction("sharedPrefs");
                startActivity(intent1);
                break;

            case R.id.btnShareData:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is a message");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            String name = data.getStringExtra("personName");
            tvName.setText(name);
        }
    }

    private String getMessageDigest(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(string.getBytes(Charset.forName("UTF-8")));
        StringBuilder hexString = new StringBuilder();
        byte[] messageDigestArray = messageDigest.digest();
        for (int i = 0; i < messageDigestArray.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigestArray[i]));
        }
        return hexString.toString();
    }
}
