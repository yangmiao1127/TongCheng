package com.example.ttt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ttt.R;
import com.example.ttt.adapter.EventAdapter;
import com.example.ttt.model.Event;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 1 on 2015/10/3.
 */
public class SaveActivity extends Activity{

    private List<Event> eventList;
    private EventAdapter adapter;
    private ListView saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
        setTitle("我的收藏");
        saveList= (ListView) findViewById(R.id.save_list);

        eventList= DataSupport.findAll(Event.class);

        adapter=new EventAdapter(SaveActivity.this,R.layout.event_activity,eventList);
        saveList.setAdapter(adapter);

        /*saveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSupport.delete(Event.class,position);
                Toast.makeText(SaveActivity.this,"已删除",Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}
