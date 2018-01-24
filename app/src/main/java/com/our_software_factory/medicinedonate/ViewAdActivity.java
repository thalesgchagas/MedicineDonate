package com.our_software_factory.medicinedonate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewAdActivity extends AppCompatActivity {

    private DatabaseReference databaseAds;
    private List<Ad> adList;

    private ListView listViewAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);

        listViewAds = (ListView) findViewById(R.id.listViewAd);

        databaseAds = FirebaseDatabase.getInstance().getReference("anuncios");

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
}
