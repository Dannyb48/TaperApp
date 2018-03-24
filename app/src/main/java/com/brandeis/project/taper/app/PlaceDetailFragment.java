package com.brandeis.project.taper.app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.maps.model.PlaceDetails;

/**
 * Created by baezd on 9/11/2017.
 */
public class PlaceDetailFragment extends Fragment {

    protected TextView ratingV, addressV, phoneV, priceV, openHoursV, currentlyOpenV;
    protected ProgressBar progressBar;
    private PlaceDetailAsyncTask task = null;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceDetailFragment newInstance(int sectionNumber, PlaceDetailAsyncTask placeDetails) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.setDetailAsyncTask(placeDetails);
        return fragment;
    }

    public PlaceDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task.setFragment(this);
        task.setProgress(getActivity().findViewById(R.id.place_detail_progress_bar));
        task.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_place_detail, container, false);
        ratingV = (TextView) rootView.findViewById(R.id.detail_rating);

        priceV = (TextView) rootView.findViewById(R.id.detail_price);

        addressV = (TextView) rootView.findViewById(R.id.detail_address);

        phoneV = (TextView) rootView.findViewById(R.id.detail_phone);

        openHoursV = (TextView) rootView.findViewById(R.id.detail_hours);

        currentlyOpenV = (TextView) rootView.findViewById(R.id.detail_open);
        return rootView;
    }



    private void setDetailAsyncTask(PlaceDetailAsyncTask t){

        task = t;
    }

}
