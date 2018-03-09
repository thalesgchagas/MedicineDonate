package com.our_software_factory.medicinedonate;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddAdActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
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
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    //we will use these constants later to pass the ad id to another activity
    public static final String AD_ID = "net.simplifiedcoding.firebasedatabaseexample.adid";

    //view objects
    private TextView expirationDateTextView;
    private AutoCompleteTextView medicineNameAutoCompleteTextView;
    private TextView qtyTextView;
    private Button saveAdButton;

    //lists to store data from firebase database
    private List<Ad> adList;
    private static List<String> medicineList;

    //our database reference object
    private DatabaseReference databaseAds;
    private DatabaseReference databaseMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        //getting the reference of medicine node
        databaseAds = FirebaseDatabase.getInstance().getReference("anuncios");

        //getting views
        medicineNameAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.medicineNameAutoCompleteTextView);
        expirationDateTextView = (TextView) findViewById(R.id.expirationDateTextView);
        qtyTextView = (TextView) findViewById(R.id.qtyTextView);

        saveAdButton = (Button) findViewById(R.id.saveAdButton);

        // TODO retrive medicineNameList from firebase here
//        databaseMedicine = FirebaseDatabase.getInstance().getReference("medicamentos");
//
//        databaseMedicine.addListenerForSingleValueEvent(new ValueEventListener() {
//            Medicine medicine;
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot medicineSnapshot : dataSnapshot.getChildren()) {
//                    medicine = medicineSnapshot.getValue(Medicine.class);
//                    if (!medicineList.contains(medicine.getNome()))
//                        medicineList.add(medicine.getNome());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("Falha ao recuperar lista de nomes de medicamentos: " + databaseError.getMessage());
//            }
//        });

        // set adapter to fill the data in suggestion list
//        ArrayAdapter<String> medicineNames = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, medicineList);
//        medicineNameAutoCompleteTextView.setAdapter(medicineNames);

        // set threshold value 3 that help us to start the searching from third character
//        medicineNameAutoCompleteTextView.setThreshold(3);

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //getting the values to save
        String medicineName = medicineNameAutoCompleteTextView.getText().toString();
        String expirationDate = expirationDateTextView.getText().toString().trim();
        String medicineQty = qtyTextView.getText().toString().trim();

        // TODO check if the value is allowed

        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our Ad
        String id = databaseAds.push().getKey();

        //creating an Ad Object
        Ad ad = new Ad(id, medicineName, expirationDate, medicineQty, user.getEmail(), user.getUid());

        //saving the Ad
        databaseAds.child(id).setValue(ad);

        //display a success toast
        Toast.makeText(this, "Anúncio concluído", Toast.LENGTH_LONG).show();
    }
}
