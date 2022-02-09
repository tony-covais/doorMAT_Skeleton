package com.example.doormat_skeleton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login (View v) {
        EditText t = findViewById(R.id.loginID);
        String input = t.getText().toString();
        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("Logging in");
        if(input == null) {
            launchMap();
        }else{
            ((TextView)findViewById(R.id.welcome)).setText("Welcome, " + input);
        }
        launchMap();

        Log.d("info", input);
    }

    public void launchMap(){
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }

}