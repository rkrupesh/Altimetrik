package com.example.altimetrik;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.customViewHolder> {

    Context mContext;
    private List<Results> dataList;

    public AdapterData(Context context, List<Results> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public customViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new customViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final customViewHolder holder, int position) {
        final Results mResult = dataList.get(position);
        holder.artist.setText("" + mResult.getArtistName());
        holder.trackName.setText("" + mResult.getTrackName());
        holder.releaseDate.setText("" + mResult.getReleaseDate().substring(0, 10));
        holder.collectionPrice.setText("$" + mResult.getCollectionPrice());
        //used to set the image source from https also given permission to manifest.xml.
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext).setDefaultRequestOptions(defaultOptions).load("" + mResult.getArtworkUrl100()).into(((customViewHolder) holder).imageView);


        holder.view.setBackgroundColor(mResult.isSelected() ? Color.CYAN : Color.WHITE);
        //used to select a particular row in recyclerview.
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResult.setSelected(!mResult.isSelected());
                holder.view.setBackgroundColor(mResult.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class customViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView artist, collectionPrice;
        private TextView trackName, releaseDate;
        public CardView cardView;
        ImageView imageView;
        public customViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            artist = itemView.findViewById(R.id.artist_name);
            trackName = itemView.findViewById(R.id.track_name);
            releaseDate = itemView.findViewById(R.id.release_date);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            collectionPrice = itemView.findViewById(R.id.collection_text);
        }
    }
}
