package com.food1.whateat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{
    private ArrayList<ItemData> items = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_item,parent,false);
        }

        TextView pName = (TextView) convertView.findViewById(R.id.list_place_name);
        TextView pPhoneNumber = (TextView) convertView.findViewById(R.id.list_phone_number);
        TextView distance = convertView.findViewById(R.id.distance);

        ItemData myItem = (ItemData) getItem(position);

        pName.setText(myItem.getPlaceName());
        pPhoneNumber.setText(myItem.getPlacePhoneNumber());
        distance.setText(myItem.getDistance()+"m");


        return convertView;
    }

    public void addItem(String pName, String pNumber, String distance){
        ItemData mItem = new ItemData();

        mItem.setPlaceName(pName);
        mItem.setPlacePhoneNumber(pNumber);
        mItem.setDistance(distance);
        items.add(mItem);
    }
}
