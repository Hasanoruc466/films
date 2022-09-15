package com.example.themoviemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchListRVAdapter extends RecyclerView.Adapter<WatchListRVAdapter.CardViewWatchlist>{
    private Context context;
    private List<WatchList> watchLists;

    public WatchListRVAdapter(Context context, List<WatchList> watchLists) {
        this.context = context;
        this.watchLists = watchLists;
    }

    @NonNull
    @Override
    public CardViewWatchlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_other, parent, false);
        return new CardViewWatchlist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewWatchlist holder, @SuppressLint("RecyclerView") int position) {
        String url = "https://image.tmdb.org/t/p/w500" + watchLists.get(position).getPoster_path();
        Picasso.with(context).load(url).into(holder.imageViewPoster);
        holder.textViewOther.setText(watchLists.get(position).getMovie_title());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("which", 2);
                intent.putExtra("watchlist", watchLists.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchLists.size();
    }

    protected class CardViewWatchlist extends RecyclerView.ViewHolder {
        private TextView textViewOther;
        private ImageView imageViewPoster;
        private CardView cardView;
        public CardViewWatchlist(@NonNull View itemView) {
            super(itemView);
            textViewOther = itemView.findViewById(R.id.textViewOtherTitle);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            cardView = itemView.findViewById(R.id.cardViewOther);
        }
    }
}
