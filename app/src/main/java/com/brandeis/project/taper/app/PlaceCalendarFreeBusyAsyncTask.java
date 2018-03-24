package com.brandeis.project.taper.app;

import android.os.AsyncTask;
import android.util.Log;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by baezd on 9/10/2017.
 */
public class PlaceCalendarFreeBusyAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private PlaceDetailActivity pda = null;
    private OkHttpClient client = new OkHttpClient();
    private Request request = null;
    private RequestBody requestBody = null;
    private Response response = null;
    private String postBody = "{\"timeMin\":\"\",\"timeMax\":\"\",\"items\":[{\"id\":\"brandeis.edu_fbcb5sbcptbh68m464kh3mm1uc@group.calendar.google.com\"}]}";
    private PlaceCalendarFreeBusyFragment f = null;
    private String date = null;
    private String time = null;
    private static String MAXHOUR = "21";

    public PlaceCalendarFreeBusyAsyncTask(PlaceDetailActivity activity) {

        pda = activity;
        date = activity.date;
        time = activity.time;


    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        JSONObject requestBodyObj = null;
        String minTime = null;
        String t[] = time.split(":");

        Log.d("CalendarAsyncTask", time);
        Log.d("CalendarAsyncTask", t[0]);

        //building up my min start time
        if(time.contains("PM")){

            if(Integer.parseInt(t[0]) == 1){

                minTime = date + "T" + "13:00" + ":00-04:00";

            }
            else if(Integer.parseInt(t[0]) == 2){

                minTime = date + "T" + "14:00" + ":00-04:00";

            }else if(Integer.parseInt(t[0]) == 3){

                minTime = date + "T" + "15:00" + ":00-04:00";

            }else if(Integer.parseInt(t[0]) == 4){

                minTime = date + "T" + "16:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 5){

                minTime = date + "T" + "17:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 6){

                minTime = date + "T" + "18:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 7){

                minTime = date + "T" + "19:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 8){

                minTime = date + "T" + "20:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 9){

                minTime = date + "T" + "21:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 10){

                minTime = date + "T" + "22:00" + ":00-04:00";

            } else if(Integer.parseInt(t[0]) == 11){

                minTime = date + "T" + "23:00" + ":00-04:00";

            }


        }
        else{

            if(Integer.parseInt(t[0]) < 10){

                minTime = date + "T" + "0" + t[0] + ":00-04:00";

            }
            else {

                minTime = date + "T" + t[0] + ":00-04:00";
            }


        }

        String maxTime = date + "T" + MAXHOUR + ":00:00-04:00";

        try {

            //building the jsob oject
            requestBodyObj  = new JSONObject(postBody);
            requestBodyObj.put("timeMin", minTime);
            requestBodyObj.put("timeMax", maxTime);
        }
        catch (JSONException e) {

            Log.e("CalendarAsyncTask", e.getMessage());
            e.printStackTrace();
        }

        //creating the json media type
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

        //buiulding the okhttp json request body
        requestBody = RequestBody.create(jsonType, requestBodyObj.toString());
        Log.d("CalendarAsyncTask", "OKHTTP REQUEST:\n" + requestBodyObj.toString());

        //building the okhttp full request
        request = new Request.Builder().url(pda.FREEBUSYURL).post(requestBody).build();


    }

    @Override
    protected Boolean doInBackground(Void... voids) {


        try{

            //running the actual okhttp request
            response = client.newCall(request).execute();
            Log.d("CalendarAsyncTask", "Running OKHTTP Request to Google Calendar API");
            return true;

        } catch (IOException e) {

            Log.e("CalendarAsyncTask", e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        try{

            String freebusy = response.body().string();
            Log.d("CalendarAsyncTask", "OKHTTP RESPONSE:\n" + freebusy);

            //Parsing the response from Google Calendar request
            JSONObject jsonFreeBusy = new JSONObject(freebusy);
            JSONArray busyArray = jsonFreeBusy.getJSONObject("calendars").getJSONObject("brandeis.edu_fbcb5sbcptbh68m464kh3mm1uc@group.calendar.google.com").getJSONArray("busy");

            //Building the times sltos for the array adapter
            if(busyArray.length() == 0) {

                String ts = null;
                String[] t = time.split(":");
                int hour = Integer.parseInt(MAXHOUR);
                int h = 0;

                if (time.contains("PM")) {

                    if (Integer.parseInt(t[0]) == 12) {

                        ts = t[0] + ":00 PM";
                        f.timeSlots.add(ts);
                    } else {

                        for (int i = Integer.parseInt(t[0]); i < 9; i++) {

                            ts = Integer.toString(i) + ":00 PM";
                            f.timeSlots.add(ts);
                        }
                    }

                } else {

                    for (int i = Integer.parseInt(t[0]); i < hour; i++) {

                        if (Integer.parseInt(t[0]) < 13) {

                            ts = t[0] + ":00";
                            if (Integer.parseInt(t[0]) == 12) {

                                ts += "PM";
                            } else {
                                ts += "AM";
                            }
                            f.timeSlots.add(ts);
                        } else {
                            switch (i) {
                                case 13:
                                    h = 1;
                                    break;
                                case 14:
                                    h = 2;
                                    break;
                                case 15:
                                    h = 3;
                                    break;
                                case 16:
                                    h = 4;
                                    break;
                                case 17:
                                    h = 5;
                                    break;
                                case 18:
                                    h = 6;
                                    break;
                                case 19:
                                    h = 7;
                                    break;
                                case 20:
                                    h = 8;
                                    break;
                                case 21:
                                    h = 9;
                                    break;
                            }

                            ts = h + ":00 PM";
                            f.timeSlots.add(ts);

                        }

                    }
                }
                f.adapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            Log.e("CalendarAsyncTask", e.getMessage());
            e.printStackTrace();
        }
        catch (JSONException e) {

            Log.e("CalendarAsyncTask", e.getMessage());
            e.printStackTrace();
        }



    }

    public void setFragment(PlaceCalendarFreeBusyFragment frag){

        f = frag;
    }
}
