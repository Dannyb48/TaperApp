package com.brandeis.project.taper.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by baezd on 9/14/2017.
 */
public class PlaceCalendarFreeBusyFragment extends Fragment{

    private PlaceCalendarFreeBusyAsyncTask task = null;
    private String item = null;
    protected TextView date = null;
    protected Button submit = null;
    protected Spinner spinner = null;
    protected ArrayList<String> timeSlots = new ArrayList<String>();
    protected ArrayAdapter<String> adapter = null;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceCalendarFreeBusyFragment newInstance(int sectionNumber, PlaceCalendarFreeBusyAsyncTask freebusy, String date) {
        PlaceCalendarFreeBusyFragment fragment = new PlaceCalendarFreeBusyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString("userDate", date);
        fragment.setArguments(args);
        fragment.setFreeBusyTask(freebusy);
        return fragment;
    }


    public PlaceCalendarFreeBusyFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task.setFragment(this);
        task.execute();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_calendar_freebusy, container, false);
        date = rootView.findViewById(R.id.user_date);
        date.setText(getArguments().getString("userDate"));
        submit = rootView.findViewById(R.id.reserve_Submit_Button);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                onSubmitButtonClicked(v);
            }
        });

        spinner = rootView.findViewById(R.id.time_slots);
        adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item, timeSlots);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                item = parent.getSelectedItem().toString();
            }
        });


        return rootView;
    }


    private void setFreeBusyTask(PlaceCalendarFreeBusyAsyncTask t){

        task = t;
    }

    public void onSubmitButtonClicked(View v){

        Intent i = new Intent(this.getContext(), ReservationFormActivity.class);
        i.putExtra("date", getArguments().getString("userDate"));
        i.putExtra("timeslot", item);
        getContext().startActivity(i);

    }

}
