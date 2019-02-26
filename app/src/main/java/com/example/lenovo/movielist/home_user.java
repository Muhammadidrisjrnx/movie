package com.example.lenovo.movielist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home_user extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JsonObjectRequest stringRequest;
    private RequestQueue requestQueue;
    private ArrayList<list_movie> arrayList;
    private list_movie list_movie;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        recyclerView = findViewById(R.id.recycler_view);
        load_movie();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_list:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void load_movie() {
        requestQueue = Volley.newRequestQueue(home_user.this);
        stringRequest = new JsonObjectRequest(Request.Method.GET, config_url.url + "list_movie.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Result");
                    arrayList = new ArrayList<list_movie>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list_movie = new list_movie();
                        list_movie.setId(jsonArray.getJSONObject(i).getInt("id_movie"));
                        list_movie.setTitle(jsonArray.getJSONObject(i).getString("title"));
                        list_movie.setImage_url(jsonArray.getJSONObject(i).getString("poster_path"));
                        list_movie.setOverview(jsonArray.getJSONObject(i).getString("overview"));
                        list_movie.setRelease_date(jsonArray.getJSONObject(i).getString("release_date"));
                        Log.d("error", "onResponse: " + list_movie.getTitle());
                        arrayList.add(list_movie);
                    }
                    adapter_home adapter_home = new adapter_home(home_user.this, arrayList);
                    recyclerView.setAdapter(adapter_home);
                    recyclerView.setLayoutManager(new LinearLayoutManager(home_user.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
}
