package com.example.lenovo.movielist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class login extends AppCompatActivity {
    private EditText editText_email, editText_password;
    private Button button_login;
    private String email, password;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_email = findViewById(R.id.edit_text_login_email);
        editText_password = findViewById(R.id.edit_text_login_password);
        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText_email.getText().toString();
                password = editText_password.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    editText_email.setError("email cannot empty");
                    editText_password.setError("password cannot empty");
                } else {
                    login_process(email,password);
                }
            }


        });
    }
    private void login_process(String em, String pass) {
        requestQueue = Volley.newRequestQueue(login.this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, config_url.url + "login.php?username=" + em + "&password=" + pass, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("Result");
                    String hasil = jsonObject.getString("Sukses");
                    if (hasil.equals("true")) {
                        Log.d("coba3", "onResponse: ");
                        Intent intent = new Intent(getApplicationContext(), home_admin.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("email_check", "admin");
                        editor.apply();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Check your email and password again",Toast.LENGTH_SHORT).show();
                        Log.e("tag", "onResponse: ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("coba5", "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("coba6", "onErrorResponse: ");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
