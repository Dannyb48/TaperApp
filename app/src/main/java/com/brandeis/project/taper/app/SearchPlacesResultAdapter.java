package com.brandeis.project.taper.app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.maps.model.PlacesSearchResult;

import java.util.ArrayList;

/**
 * Created by baezd on 9/9/2017.
 */
public class SearchPlacesResultAdapter extends RecyclerView.Adapter<SearchPlacesResultAdapter.MyViewHolder>{

    private ArrayList<PlacesSearchResult> dataset = null;
    private SearchResultsActivity sra = null;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView placeId, placeName, placeAddress;
        private RatingBar placeRating;

        public MyViewHolder(View v){
            super(v);

            placeId = (TextView) v.findViewById(R.id.placeID);
            placeName = (TextView) v.findViewById(R.id.placeName);
            placeAddress = (TextView) v.findViewById(R.id.placeAddress);
            placeRating = (RatingBar) v.findViewById(R.id.placeRating);

        }
    }

    public SearchPlacesResultAdapter(SearchResultsActivity activity, ArrayList<PlacesSearchResult> psr){

        dataset = psr;
        sra = activity;
    }

    @Override
    public SearchPlacesResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_card_layout, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(SearchPlacesResultAdapter.MyViewHolder holder, int position) {

        final TextView placeId = holder.placeId;
        TextView placeName = holder.placeName;
        TextView placeAdd = holder.placeAddress;
        RatingBar placeRate = holder.placeRating;

        placeId.setText(dataset.get(position).placeId);
        placeName.setText(dataset.get(position).name);
        placeAdd.setText(dataset.get(position).vicinity);
        placeRate.setRating(dataset.get(position).rating);

        placeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent placeDetail = new Intent(sra, PlaceDetailActivity.class);
                placeDetail.putExtra("placeID", placeId.getText());
                placeDetail.putExtra("API_KEY", sra.API_KEY);
                sra.startActivity(placeDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
