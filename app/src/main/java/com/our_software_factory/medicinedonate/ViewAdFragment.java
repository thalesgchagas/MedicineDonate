package com.our_software_factory.medicinedonate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class ViewAdFragment extends Fragment
{
    private static final String TAG = "Visualizar Anúncio";
    private ListView listViewAds;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_view_ad, container, false);

        listViewAds = (ListView) view.findViewById(R.id.listViewAd);

        return view;
    }
}
