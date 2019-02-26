package com.example.lenovo.movielist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class details extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView_title, textView_overview, textView_releasedate;
    private int id;
    private String title, image_url, overview, release_date;
    private FloatingActionButton floating_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initial();
        floating_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(details.this, edit_movie.class);
                intent.putExtra("id_movie", id);
                intent.putExtra("title", title);
                intent.putExtra("overview", overview);
                intent.putExtra("release_date", release_date);
                startActivity(intent);
            }
        });
    }

    private void initial() {
        floating_action = findViewById(R.id.floating_action);
        imageView = findViewById(R.id.image_view);
        textView_title = findViewById(R.id.text_title);
        textView_overview = findViewById(R.id.text_overview);
        textView_releasedate = findViewById(R.id.text_release_date);
        id = getIntent().getExtras().getInt("id_movie");
        title = getIntent().getExtras().getString("title");
        image_url = getIntent().getExtras().getString("image_url");
        overview = getIntent().getExtras().getString("overview");
        release_date = getIntent().getExtras().getString("release_date");
        Picasso.get().load(image_url).placeholder(R.color.grey).into(imageView);
        textView_title.setText(title);
        textView_releasedate.setText(release_date);
        textView_overview.setText(overview);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_movie:
                Intent intent = new Intent(details.this,edit_movie.class);
                intent.putExtra("id_movie",id);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}