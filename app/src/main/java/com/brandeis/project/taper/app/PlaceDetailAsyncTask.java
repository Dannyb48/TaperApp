package com.brandeis.project.taper.app;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;

import java.io.IOException;


/**
 * Created by baezd on 9/10/2017.
 */
public class PlaceDetailAsyncTask extends AsyncTask<Void, Void, Boolean>{
    private PlaceDetailActivity pda = null;
    private ProgressBar pb = null;
    private PlaceDetailFragment f = null;
    private PlaceDetails pd = null;

    public PlaceDetailAsyncTask(PlaceDetailActivity activity) {

        pda = activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try{

            pd = PlacesApi.placeDetails(pda.context, pda.placeId).await();
            Log.d(pda.TAG,"Running Google Place Detail API");
            //f.pd = pd;
            return true;

        }
        catch(ApiException apie){

            apie.printStackTrace();

        }
        catch(InterruptedException ie){

            ie.printStackTrace();

        }
        catch(IOException ioe){

            ioe.printStackTrace();

        }

        return false;
    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        f.ratingV.setText(Float.toString(pd.rating)+ "\n");
        f.addressV.setText(pd.formattedAddress + "\n");
        f.phoneV.setText(pd.formattedPhoneNumber + "\n");
        f.openHoursV.setText("");
        for(String h : pd.openingHours.weekdayText){

            f.openHoursV.append(h + "\n");
        }

        if(pd.openingHours.openNow){

            f.currentlyOpenV.setText("Open");
        }
        else {

            f.currentlyOpenV.setText("Closed");
        }

        //pda.placeReviews = pd.reviews;
        pb.setVisibility(View.GONE);

    }


    public void setFragment(PlaceDetailFragment frag){

        f = frag;

    }

    public void setProgress(View pb){

        this.pb = (ProgressBar) pb;

    }

}


