package com.example.ttt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ttt.R;
import com.example.ttt.model.City;

import java.util.List;

/**
 * Created by 1 on 2015/10/1.
 */
public class CityAdapter extends ArrayAdapter<City>{
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public City getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private int resourceId;


    public CityAdapter(Context context, int textViewResourceId, List<City> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
           view =LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.cityName=(TextView) view.findViewById(R.id.city_name);
            viewHolder.cityId=(TextView) view.findViewById(R.id.city_id);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.cityName.setText(city.getCityName());
        viewHolder.cityId.setText(city.getCityId());

        return view;
    }
    class ViewHolder{
        TextView cityName;
        TextView cityId;
    }


}
