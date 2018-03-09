package com.our_software_factory.medicinedonate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddAdFragment extends Fragment
{
    private FloatingActionButton buttonAddAd;
    private DatabaseReference databaseAds;

    private ListView listViewAds;
    private ArrayList<Ad> adList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        databaseAds = FirebaseDatabase.getInstance().getReference("anuncios");

        //TODO load list
        //attaching value event listener
        databaseAds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //clearing the previous ad list
                adList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting ad
                    Ad ad = postSnapshot.getValue(Ad.class);

                    if (ad.getUserId().equals(user.getUid()))
                    {
                        //add to the list
                        adList.add(ad);
                    }
                }

                //creating adapter
                AdList adListAdapter = new AdList(getActivity(), adList);
                //attaching adapter to the list view
                listViewAds.setAdapter(adListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_add_ad, container, false);
        listViewAds = (ListView) view.findViewById(R.id.listViewMyAds);

        buttonAddAd = (FloatingActionButton) view.findViewById(R.id.buttonAddAd);
        if(buttonAddAd != null) {
            buttonAddAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddAdActivity.class);
                    startActivity(intent);
                }
            });
        }

        listViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad ad = adList.get(i);
                showUpdateDeleteDialog(ad.getAdId());
            }
        });

        return view;
    }

    private void showUpdateDeleteDialog(final String id)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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
            public void onClick(View view)
            {
                    deleteAd(id);
                    alertDialog.dismiss();
            }
        });
    }

    private boolean updateAd(String id, String medicineName, String expirationDate, String qty)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //getting the specified ad reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("anuncios").child(id);

        //updating ad
        Ad ad = new Ad(id, medicineName, expirationDate, qty, user.getEmail(), user.getUid());

        databaseReference.setValue(ad);
        Toast.makeText(getContext(), "Anuncio atualizado", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean deleteAd(String id)
    {
        //getting the specified ad reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("anuncios").child(id);

        //removing ad
        databaseReference.removeValue();
        Toast.makeText(getContext(), "Anuncio removido", Toast.LENGTH_LONG).show();

        return true;
    }
}