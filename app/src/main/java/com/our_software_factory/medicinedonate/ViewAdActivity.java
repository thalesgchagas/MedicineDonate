package com.our_software_factory.medicinedonate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ViewAdActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private DatabaseReference databaseAds;
    private List<Ad> adList;

    private ListView listViewAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);

        databaseAds = FirebaseDatabase.getInstance().getReference("anuncios");

        listViewAds = (ListView) findViewById(R.id.listViewAd);

        adList = new ArrayList<>();

        listViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad ad = adList.get(i);
                showUpdateDeleteDialog(ad.getAdId());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //attaching value event listener
        databaseAds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous ad list
                adList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting ad
                    Ad ad = postSnapshot.getValue(Ad.class);
                    //add to the list
                    adList.add(ad);
                }

                //creating adapter
                AdList adListAdapter = new AdList(ViewAdActivity.this, adList);
                //attaching adapter to the list view
                listViewAds.setAdapter(adListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // TODO include other updatetable parameters
    private boolean updateAd(String id, String medicineName, String expirationDate, String qty)
    {
        //getting the specified ad reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("anuncios").child(id);

        //updating ad
        Ad ad = new Ad(id, medicineName, expirationDate, qty);

        databaseReference.setValue(ad);
        Toast.makeText(getApplicationContext(), "Anuncio atualizado", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String id)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        //edit ad views
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final TextView textViewExpirationDate = (TextView) dialogView.findViewById(R.id.textViewExpirationDate);
        final Button buttonUpdateDate = (Button) dialogView.findViewById(R.id.buttonUpdateDate);
        final EditText editTextUpdateQty = (EditText) dialogView.findViewById(R.id.editTextUpdateQty);

        //update views
        final Button buttonUpdateAd = (Button) dialogView.findViewById(R.id.buttonUpdateAd);
        final Button buttonDeleteAd = (Button) dialogView.findViewById(R.id.buttonDeleteAd);

        dialogBuilder.setTitle("Atualizar anuncio");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdateAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newMedicinename = editTextName.getText().toString();
                String newExpirationDate = textViewExpirationDate.getText().toString();
                String newQty = editTextUpdateQty.getText().toString();

                if(!TextUtils.isEmpty(newMedicinename))
                {
                    updateAd(id, newMedicinename, newExpirationDate, newQty);
                    alertDialog.dismiss();
                }
            }
        });

        buttonDeleteAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO delete ad
            }
        });
    }

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
        ((TextView) findViewById(R.id.textViewExpirationDate)).setText(dateFormat.format(calendar.getTime()));
    }

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void updateDate(View view)
    {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }
}
