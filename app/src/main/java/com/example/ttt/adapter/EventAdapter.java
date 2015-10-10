package com.example.ttt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ttt.R;
import com.example.ttt.model.Event;

import java.util.List;

/**
 * Created by 1 on 2015/10/1.
 */
public class EventAdapter extends ArrayAdapter<Event>{

    private int resourceId;

    public EventAdapter(Context context, int textViewResourceId, List<Event> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.listTitle= (TextView) view.findViewById(R.id.list_title);
            viewHolder.startTime= (TextView) view.findViewById(R.id.start_time);
            viewHolder.stopTime=(TextView) view.findViewById(R.id.end_time);
            viewHolder.id=(TextView) view.findViewById(R.id.event_id);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();

        }

        viewHolder.listTitle.setText(event.getTitle());
        viewHolder.startTime.setText(event.getStartTime());
        viewHolder.stopTime.setText(event.getEndTime());
        viewHolder.id.setText(event.getEventId());
        return view;
    }
    class ViewHolder{
        TextView listTitle;
        TextView startTime;
        TextView stopTime;
        TextView id;
    }
}