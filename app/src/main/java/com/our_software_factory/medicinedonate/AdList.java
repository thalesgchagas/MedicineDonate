package com.our_software_factory.medicinedonate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdList extends ArrayAdapter<Ad> {
    private Activity context;
    private List<Ad> adList;

    public AdList(Activity context, List<Ad> adList)
    {
        super(context, R.layout.layout_ad_list, adList);
        this.context = context;
        this.adList = adList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_ad_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewContent = (TextView) listViewItem.findViewById(R.id.textViewContent);
//        TextView textViewLab = (TextView) listViewItem.findViewById(R.id.textViewLab);
//        TextView textViewComponent = (TextView) listViewItem.findViewById(R.id.textViewComponent);
//        TextView textViewLabel = (TextView) listViewItem.findViewById(R.id.textViewLabel);
//        TextView textViewType = (TextView) listViewItem.findViewById(R.id.textViewType);
        TextView textViewExpirationDate = (TextView) listViewItem.findViewById(R.id.textViewExpirationDate);
        TextView textViewQty = (TextView) listViewItem.findViewById(R.id.textViewQty);

        Ad ad = adList.get(position);

        textViewName.setText(ad.getMedicineName());
//        textViewContent.setText(ad.getContent());
//        textViewLab.setText(ad.getLab());
//        textViewComponent.setText(ad.getComponent());
//        textViewLabel.setText(ad.getLabel());
//        textViewType.setText(ad.getYpe());
        textViewExpirationDate.setText(ad.getExpirationDate());
        textViewQty.setText(ad.getQty());

        return listViewItem;
    }
}
