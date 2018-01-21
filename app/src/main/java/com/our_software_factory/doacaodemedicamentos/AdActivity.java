package com.our_software_factory.doacaodemedicamentos;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AdActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        Calendar calendar = new GregorianCalendar(year, month, day);
        setDate(calendar);
    }

    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar)
    {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.expirationDateTextView)).setText(dateFormat.format(calendar.getTime()));
    }

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void datePicker(View view)
    {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        //  TODO datePickerFragment.show(getSupportFragmentManager(), "date");
        datePickerFragment.show(getFragmentManager(), "date");
    }

    //we will use these constants later to pass the artist name and id to another activity
    public static final String AD_ID = "net.simplifiedcoding.firebasedatabaseexample.adid";

    //view objects
    TextView expirationDateTextView;
    MultiAutoCompleteTextView medicineNameMultiAutoCompleteTextView;
    TextView qtyTextView;
    Button saveAdButton;

    //a list to store all the ad from firebase database
    List<Ad> adList;

    //our database reference object
    DatabaseReference databaseAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        //getting the reference of medicine node
        databaseAds = FirebaseDatabase.getInstance().getReference("ADS");

        //getting views
        expirationDateTextView = (TextView) findViewById(R.id.expirationDateTextView);
        medicineNameMultiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.medicineNameMultiAutoCompleteTextView);
        qtyTextView = (TextView) findViewById(R.id.qtyTextView);

        saveAdButton = (Button) findViewById(R.id.saveAdButton);

        //list to store ads
        adList = new ArrayList<>();

        //adding an onclicklistener to button
        saveAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addAd()
                //the method is defined below
                //this method is actually performing the write operation
                addAd();
            }
        });
    }

     /*
    * This method is saving a new ad to the
    * Firebase Realtime Database
    * */
     private void addAd()
     {
         //getting the values to save
         String medicineName = medicineNameMultiAutoCompleteTextView.getText().toString();
         String expirationDate = expirationDateTextView.getText().toString().trim();
         String medicineQty = qtyTextView.getText().toString().trim();

         // TODO check if the value is allowed

         //getting a unique id using push().getKey() method
         //it will create a unique id and we will use it as the Primary Key for our Ad
         try
         {
             String id = databaseAds.push().getKey();

             //creating an Ad Object
             Ad ad = new Ad(medicineName, expirationDate, medicineQty);

             //saving the Ad
             databaseAds.child(id).setValue(ad);

             //display a success toast
             Toast.makeText(this, "Anúncio concluído", Toast.LENGTH_LONG).show();
         } catch (Exception e)
         {
             Toast.makeText(this, "Falha ao salvar no FB", Toast.LENGTH_LONG).show();
         }
     }
}
