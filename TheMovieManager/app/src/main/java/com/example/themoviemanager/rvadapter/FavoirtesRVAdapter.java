package com.example.themoviemanager.rvadapter;

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

import com.example.themoviemanager.DetailsActivity;
import com.example.themoviemanager.R;
import com.example.themoviemanager.models.Favorites;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoirtesRVAdapter extends RecyclerView.Adapter<FavoirtesRVAdapter.CardViewFavorites>{
    private Context context;
    private List<Favorites> favorites;

    public FavoirtesRVAdapter(Context context, List<Favorites> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public CardViewFavorites onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_other, parent, false);
        return new CardViewFavorites(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewFavorites holder, @SuppressLint("RecyclerView") int position) {
        String url = "https://image.tmdb.org/t/p/w500" + favorites.get(position).getPoster_path();
        Picasso.with(context).load(url).into(holder.imageViewPoster);
        holder.textViewOther.setText(favorites.get(position).getMovie_title());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("which", 1);
                intent.putExtra("fav", favorites.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    protected class CardViewFavorites extends RecyclerView.ViewHolder {
        private TextView textViewOther;
        private ImageView imageViewPoster;
        private CardView cardView;
        public CardViewFavorites(@NonNull View itemView) {
            super(itemView);
            textViewOther = itemView.findViewById(R.id.textViewOtherTitle);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            cardView = itemView.findViewById(R.id.cardViewOther);
        }
    }
}
