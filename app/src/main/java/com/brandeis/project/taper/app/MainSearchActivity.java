package com.brandeis.project.taper.app;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;


public class MainSearchActivity extends ActionBarActivity {

    private static final String TAG = "TaperSearchActivity";
    SharedPreferences searchValues = null;
    Calendar c = Calendar.getInstance();
    EditText dateBox = null;
    EditText timeBox = null;
    EditText locBox = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        //getting sharedpreference to store the search result
         searchValues = getSharedPreferences(getString(R.string.sp_file_name), MODE_PRIVATE);


        //filling in the current date for the date editbox
        int month = c.get(Calendar.MONTH) + 1;
        String currentDate = c.get(Calendar.YEAR) + "-" + month + "-" + c.get(Calendar.DAY_OF_MONTH);
        dateBox = (EditText) findViewById(R.id.search_date);
        dateBox.setText(currentDate);

        //filling in the current time for the time editbox
        String minute = null;
        String hour = null;
        String currentTime = null;

        String am_pm = (c.get(Calendar.HOUR_OF_DAY) < 12) ? "AM" : "PM";
        if(c.get(Calendar.MINUTE) < 10){

            minute = "0" + c.get(Calendar.MINUTE);

        }

        if(c.get(Calendar.HOUR) == 0){

            hour = "12";
        }

        if((hour == null) && (minute == null)){

            currentTime = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + am_pm;
        }
        else if((hour != null) && (minute == null)){

            currentTime = hour + ":" + c.get(Calendar.MINUTE) + am_pm;
        }
        else if((hour == null) && (minute != null)){

            currentTime = c.get(Calendar.HOUR) + ":" + minute + am_pm;
        }
        else if((hour != null) && (minute != null)){

            currentTime = hour + ":" + minute + am_pm;
        }


        timeBox = (EditText) findViewById(R.id.search_time);
        timeBox.setText(currentTime);

        locBox = (EditText) findViewById(R.id.search_location);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onDateButtonClicked(View v){

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        EditText db = (EditText)findViewById(R.id.search_date);
                        db.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void onTimeButtonClicked(View v){


        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        String am_pm = (hourOfDay < 12) ? "AM" : "PM";

                        String min = null;
                        String hour = null;
                        String currentTime = null;
                        if(minute < 10){

                            min = "0" + minute;

                        }

                        switch(hourOfDay){
                            case 0:
                                hour = "12";
                                break;
                            case 13:
                                hour = "1";
                                break;
                            case 14:
                                hour = "2";
                                break;
                            case 15:
                                hour = "3";
                                break;
                            case 16:
                                hour = "4";
                                break;
                            case 17:
                                hour = "5";
                                break;
                            case 18:
                                hour = "6";
                                break;
                            case 19:
                                hour = "7";
                                break;
                            case 20:
                                hour = "8";
                                break;
                            case 21:
                                hour = "9";
                                break;
                            case 22:
                                hour = "10";
                                break;
                            case 23:
                                hour = "11";
                                break;
                        }

                        if((hour == null) && (min == null)){

                            currentTime = hourOfDay + ":" + minute;
                        }
                        else if((hour != null) && (min == null)){

                            currentTime = hour + ":" + minute;
                        }
                        else if((hour == null) && (min != null)){

                            currentTime = hourOfDay + ":" + min;
                        }
                        else if((hour != null) && (min != null)){

                            currentTime = hour + ":" + min;
                        }

                        EditText tb = (EditText)findViewById(R.id.search_time);
                        tb.setText(currentTime + am_pm);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void onSearchButtonClicked(View v) throws Exception {

        TextInputLayout til = (TextInputLayout)findViewById(R.id.text_input_layout);

        if(locBox.getText().toString().trim().length() !=0){

            //saving the search parameters in the shared preferences as a global datastore to retrieve later.
            SharedPreferences.Editor editor = searchValues.edit();
            editor.putString("date", dateBox.getText().toString());
            editor.putString("time", timeBox.getText().toString());
            editor.putString("zip", locBox.getText().toString());

            editor.commit();

            //starting the search result activity
            Intent searchResult = new Intent(this, SearchResultsActivity.class);
            startActivity(searchResult);

        }
        else {

            til = (TextInputLayout)findViewById(R.id.text_input_layout);
            til.setError("Enter a zip code");

        }


    }
}
