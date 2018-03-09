package com.our_software_factory.medicinedonate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ViewAdFragment extends Fragment
{
    private DatabaseReference databaseAds;

    private ListView listViewAds;
    private ArrayList<Ad> adList = new ArrayList<>();

    public ViewAdFragment() {}

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
                AdList adListAdapter = new AdList(getActivity(), adList);
                //attaching adapter to the list view
                listViewAds.setAdapter(adListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_view_ad, container, false);
        listViewAds = (ListView) view.findViewById(R.id.listViewAd);

        listViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad ad = adList.get(i);
//                showUpdateDeleteDialog(ad.getAdId());
            }
        });

        return view;
    }
}