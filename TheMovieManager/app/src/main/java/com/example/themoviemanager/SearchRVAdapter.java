package com.example.themoviemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.Cardview>{
    private Context context;
    private List<Results> results;

    public SearchRVAdapter(Context context, List<Results> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public Cardview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_search, parent, false);
        return new Cardview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cardview holder, @SuppressLint("RecyclerView") int position) {
        String date = results.get(position).getRelease_date();
        if(date.length()!=0){
            String year = date.substring(0, 4);
            holder.textView.setText(results.get(position).getTitle() + "   -   "+ year);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("which", 0);
                intent.putExtra("result", results.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    protected class Cardview extends RecyclerView.ViewHolder {
        private TextView textView;
        private CardView cardView;
        public Cardview(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewList);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
