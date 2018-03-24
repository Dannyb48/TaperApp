package com.brandeis.project.taper.app;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;

import java.util.ArrayList;


public class SearchResultsActivity extends ActionBarActivity {

    //Place your own API Key here
    protected static final String API_KEY = "";
    protected static final String TAG = "TaperSearchResActivity";
    private static RecyclerView.Adapter searchadapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    protected SharedPreferences sp = null;
    protected GeoApiContext context = null;
    protected String location = null;
    protected ArrayList<PlacesSearchResult> placesSearchResultArrayList = new ArrayList<PlacesSearchResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //building the context for the GeoApiContext
        context = new GeoApiContext.Builder().apiKey(API_KEY).build();
        
        //get the location from preferences
         sp = getSharedPreferences(getString(R.string.sp_file_name), MODE_PRIVATE);
        location = sp.getString("zip", "01604");

        SearchAsyncTask search = new SearchAsyncTask(this);
        search.execute();

        recyclerView = (RecyclerView) findViewById(R.id.results_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchadapter = new SearchPlacesResultAdapter(this, placesSearchResultArrayList);
        recyclerView.setAdapter(searchadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
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

    public void refreshView(){

        //once the API command returns need to notify the adapter
        searchadapter.notifyDataSetChanged();

    }

}
