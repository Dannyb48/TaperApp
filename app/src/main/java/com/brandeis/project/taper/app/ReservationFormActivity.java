package com.brandeis.project.taper.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ReservationFormActivity extends ActionBarActivity {

    private String date, time;
    private SQLiteDatabase taperDB;
    private MySQLiteManager dbHelper;
    private TextView datebox, timeBox;
    private EditText nameBox, emailBox, phoneBox;
    private TextInputLayout nil, eil, pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        //retrieving the information passed from the PlaceCalendarFragment
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("timeslot");

        //Building the db helper
        dbHelper = new MySQLiteManager(this);
        taperDB = dbHelper.getWritableDatabase();

        //getting boxes
        datebox = (TextView) findViewById(R.id.form_date);
        datebox.setText(date);
        timeBox = (TextView) findViewById(R.id.form_time);
        timeBox.setText(time);
        nameBox = (EditText)findViewById(R.id.form_name);
        emailBox = (EditText) findViewById(R.id.form_email);
        phoneBox = (EditText) findViewById(R.id.form_phone);

        //getting input layouts

        nil = (TextInputLayout) findViewById(R.id.form_name_input_layout);
        eil = (TextInputLayout) findViewById(R.id.form_email_input_layout);
        pil = (TextInputLayout) findViewById(R.id.form_phone_input_layout);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservation_form, menu);
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


    public void onConfirmButtonClicked(View v){

        if((nameBox.getText().toString().trim().length() !=0) && (emailBox.getText().toString().trim().length() !=0) && (phoneBox.getText().toString().trim().length() !=0)){

            Toast.makeText(this, "Saving Reservation...", Toast.LENGTH_SHORT).show();
            if (saveReservation()){
                //trying to reset all the other activities so that this particular use case flow is complete.
                Intent i = new Intent(this, MainSearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();

            }
            else {

                Toast.makeText(this, "Failed to save reservation", Toast.LENGTH_SHORT).show();
            }
        }

        //form validation code.

        else if ((nameBox.getText().toString().trim().length() ==0) && (emailBox.getText().toString().trim().length() !=0) && (phoneBox.getText().toString().trim().length() !=0)){

            nil.setError("Enter your name.");
        }
        else if ((nameBox.getText().toString().trim().length() !=0) && (emailBox.getText().toString().trim().length() ==0) && (phoneBox.getText().toString().trim().length() !=0)){

            eil.setError("Enter your e-mail address.");
        }

        else if ((nameBox.getText().toString().trim().length() !=0) && (emailBox.getText().toString().trim().length() !=0) && (phoneBox.getText().toString().trim().length() ==0)){

            pil.setError("Enter your phone number.");
        }

        else if ((nameBox.getText().toString().trim().length() !=0) && (emailBox.getText().toString().trim().length() ==0) && (phoneBox.getText().toString().trim().length() ==0)){

            eil.setError("Enter your e-mail address.");
            pil.setError("Enter your phone number.");
        }
        else if ((nameBox.getText().toString().trim().length() ==0) && (emailBox.getText().toString().trim().length() !=0) && (phoneBox.getText().toString().trim().length() ==0)){

            nil.setError("Enter your name.");
            pil.setError("Enter your phone number.");
        }
        else if ((nameBox.getText().toString().trim().length() ==0) && (emailBox.getText().toString().trim().length() ==0) && (phoneBox.getText().toString().trim().length() !=0)){

            nil.setError("Enter your name.");
            eil.setError("Enter your e-mail address.");
        }
        else if ((nameBox.getText().toString().trim().length() ==0) && (emailBox.getText().toString().trim().length() ==0) && (phoneBox.getText().toString().trim().length() ==0)){

            nil.setError("Enter your name.");
            eil.setError("Enter your e-mail address.");
            pil.setError("Enter your phone number.");
        }

    }

    private boolean saveReservation(){

        //putting together the values from the form editboxes to insert them into the database.
        ContentValues values = new ContentValues(5);
        values.put(dbHelper.getTABLE_COLUMNS()[1], date);
        values.put(dbHelper.getTABLE_COLUMNS()[2], time);
        values.put(dbHelper.getTABLE_COLUMNS()[3], nameBox.getText().toString());
        values.put(dbHelper.getTABLE_COLUMNS()[4], emailBox.getText().toString());
        values.put(dbHelper.getTABLE_COLUMNS()[5], phoneBox.getText().toString());
        long r = taperDB.insert(dbHelper.getTABLE_NAME(), dbHelper.getTABLE_COLUMNS()[4], values);

        if (r != -1){

            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taperDB.close();
    }
}
