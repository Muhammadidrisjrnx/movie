package com.example.lenovo.movielist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class adapter_home extends RecyclerView.Adapter<adapter_home.Holder> {
    Context context;
    ArrayList<list_movie>arrayList;

    public adapter_home(Context context, ArrayList<list_movie>arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final list_movie list_movie = arrayList.get(position);
        holder.textView.setText(list_movie.getTitle());
        Picasso.get().load(list_movie.getImage_url()).placeholder(R.color.grey).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = list_movie.getId();
                final String title = list_movie.getTitle();
                final String image_url = list_movie.getImage_url();
                final String overview = list_movie.getOverview();
                final String release_date = list_movie.getRelease_date();
                Intent intent = new Intent(context,details.class);
                intent.putExtra("id_movie",id);
                intent.putExtra("title",title);
                intent.putExtra("image_url",image_url);
                intent.putExtra("overview",overview);
                intent.putExtra("release_date",release_date);
                context.startActivity(intent);
            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Movie");
                builder.setMessage("Are you want to delete this movie?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final int id = list_movie.getId();
                        delete_movie(id);
                        arrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,arrayList.size());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void delete_movie(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, config_url.url + "delete_movie.php?id_movie="+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("Result");
                    String hasil = jsonObject.getString("Sukses");
                    if (hasil.equals("true")){
                        Log.d("coba tes delete1", "onResponse: ");
                    }else {
                        Log.d("coba tes delete2", "onResponse: ");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("coba tes delete3", "onResponse: ");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("coba tes delete4", "onResponse: ");
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CardView cardView;
        private Button button_delete;
        public Holder(@NonNull View itemView) {
            super(itemView);
            button_delete = itemView.findViewById(R.id.button_delete);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_title);
        }
    }
}
