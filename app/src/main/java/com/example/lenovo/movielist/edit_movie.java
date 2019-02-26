package com.example.lenovo.movielist;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

public class edit_movie extends AppCompatActivity {
    private String title,release_date,overview,title2,release_date2,overview2;
    private int id_movie;
    private EditText editText_title,editText_overview,editText_release;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        initial();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title2 =editText_title.getText().toString();
                overview2 =editText_overview.getText().toString();
                release_date2 =editText_release.getText().toString();
                if (title2.isEmpty()&&overview2.isEmpty()&&release_date2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cannot empty",Toast.LENGTH_SHORT).show();
                }else {
                    edit_data(title2,overview2,release_date2,id_movie);
                }
            }
        });
    }
    private void initial() {
        id_movie = getIntent().getExtras().getInt("id_movie");
        title = getIntent().getExtras().getString("title");
        overview = getIntent().getExtras().getString("overview");
        release_date = getIntent().getExtras().getString("release_date");
        editText_title = findViewById(R.id.edit_text_title_movie);
        editText_overview = findViewById(R.id.edit_text_overview_movie);
        editText_release = findViewById(R.id.edit_text_release_movie);
        button = findViewById(R.id.btn_save_edit_movie);
        editText_title.setHint(title);
        editText_overview.setHint(overview);
        editText_release.setHint(release_date);
    }

    private void edit_data(String title2, String overview2, String release_date2,int id_movie) {
        RequestQueue requestQueue = Volley.newRequestQueue(edit_movie.this);
        Log.d("url", "login_process: "+config_url.url+ "edit_movie.php?title=" + title2 + "&overview=" + overview2+"&release_date="+release_date2+"&id_movie="+id_movie);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, config_url.url + "edit_movie.php?title=" + title2 + "&overview=" + overview2+"&release_date="+release_date2+"&id_movie="+id_movie, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("Result");
                    String hasil = jsonObject.getString("Sukses");
                    if (hasil.equals("true")) {
                        Log.d("coba3", "onResponse: ");
                        Intent intent = new Intent(getApplicationContext(), home_admin.class);
                        startActivity(intent);
                        finish();
                    } else {
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
