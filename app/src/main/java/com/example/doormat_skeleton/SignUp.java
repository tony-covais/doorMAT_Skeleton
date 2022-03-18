package com.example.doormat_skeleton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    //Initializing variables
    private EditText textUsername, textPassword, textEmail;
    private Button buttonInsert;
    private FloatingActionButton backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Reference variables by ID's from XML format
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        textEmail = findViewById(R.id.email);
        buttonInsert = findViewById(R.id.sign_in);

        //Set an Onclick Listener
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Insert Function
                insertData();
            }
        });

    }

    public void back(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void insertData() {
        //Defining variables for getting info from user input
        final String username = textUsername.getText().toString();
        final String password = textPassword.getText().toString();
        final String email = textEmail.getText().toString();

        //Validate inputs
        if (username.isEmpty()) {
            textUsername.setError("Please enter name");
        } else if (password.isEmpty()) {
            textPassword.setError("Please enter password");
        } else if (email.isEmpty()) {
            textEmail.setError("Please enter email");
        }else{
            //Get the insert URL
            String insert_url = "http://34.203.214.232/mysite/insertData.php";
            //Communicate with backend
            StringRequest stringRequest = new StringRequest(Request.Method.POST, insert_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Set Response
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (success.equals("1")){
                                    Toast.makeText(SignUp.this, "User data has been inserted", Toast.LENGTH_SHORT ).show();

                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUp.this, "Unable to insert data" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                //Set Parameters that will also match with the API

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Create hashmap, equivalent to an array
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("email", email);

                    return params;
                }
            };
            //Set a requestQueue, enables Volley communication to backend
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}