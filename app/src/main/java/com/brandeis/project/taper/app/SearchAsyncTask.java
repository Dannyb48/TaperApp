package com.brandeis.project.taper.app;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.maps.GeocodingApi;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by baezd on 9/9/2017.
 */
public class SearchAsyncTask extends AsyncTask<Void, Void, Boolean>{

    private GeocodingResult[] results = null;
    private PlacesSearchResponse psr = null;
    private LatLng loc = null;
    private SearchResultsActivity sra = null;
    private ProgressBar pb = null;

    public SearchAsyncTask(SearchResultsActivity activity) {

        sra = activity;
        pb = (ProgressBar)sra.findViewById(R.id.progress_bar);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    protected Boolean doInBackground(Void... voids) {


        try {

            //getting geocoding for the location first.
            results = GeocodingApi.geocode(sra.context, sra.location).await();
            Log.d(sra.TAG,"Running Geocoding API");
            loc = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
            //Log.d(sra.TAG, results[0].formattedAddress);
            //Log.d(sra.TAG, loc.toString());

            //using the geocoded location to get a query of places
            psr = PlacesApi.nearbySearchQuery(sra.context, loc).radius(8047).keyword("Barbershops").await();
            Log.d(sra.TAG, "Running Places API");

            return true;
            //String pr = psres.photos[0].photoReference;
            //PhotoResult phr = PlacesApi.photo(context, pr).await();
        }

        catch(ApiException apie){

            Log.e(sra.TAG, apie.getMessage());
            apie.printStackTrace();

        }
        catch(InterruptedException ie){

            Log.e(sra.TAG, ie.getMessage());
            ie.printStackTrace();

        }
        catch(IOException ioe){

            Log.e(sra.TAG, ioe.getMessage());
            ioe.printStackTrace();
        }

        return false;

    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        sra.placesSearchResultArrayList.addAll(Arrays.asList(psr.results));
        pb.setVisibility(View.GONE);
        sra.refreshView();
    }
}
