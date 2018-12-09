package com.example.jasongomez.savinginstancestate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private TextView name, gender;
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        name = findViewById(R.id.tvName);
        gender = findViewById(R.id.tvGender);

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (getIntent().getAction()) {
            case "goToSecond":
                List<Person> personList;
                personList = getIntent().getParcelableArrayListExtra("person");
                Toast.makeText(this,
                        String.valueOf(personList.size()), Toast.LENGTH_SHORT).show();
                for (Person person : personList) {
                    Log.d(TAG, "onResume: " + person.toString());
                }
                break;

            case "goToSecondForResult":
                Person person = getIntent().getParcelableExtra("person");
                Intent intent = new Intent();
                intent.putExtra("personName", person.getName());
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            case "sharedPrefs":
                SharedPreferences sharedPreferences = getSharedPreferences("sp",
                        Context.MODE_PRIVATE);
                String personName = sharedPreferences.getString("name", "");
                String personGender = sharedPreferences.getString("gender", "");
                name.setText(personName);
                gender.setText(personGender);
                break;
        }
    }
}
