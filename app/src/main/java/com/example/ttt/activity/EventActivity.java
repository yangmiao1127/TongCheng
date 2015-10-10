package com.example.ttt.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt.R;
import com.example.ttt.adapter.EventAdapter;
import com.example.ttt.model.Event;
import com.example.ttt.util.HttpCallbackListener;
import com.example.ttt.util.HttpUtil;
import com.example.ttt.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2015/10/1.
 */
public class EventActivity extends Activity {

    private ProgressDialog progressDialog;

    private ListView listView;
    private static final int DDD=1;
    private List<Event> eventList = new ArrayList<Event>();
    private EventAdapter mAdapter;
    private String id;
    private String name;
    private String type;

    private ImageView weatherImage;
    private TextView weather;
    private TextView temp;
    private TextView time;
    private String urlEncode;

    private android.os.Handler handler=new android.os.Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DDD:
                    eventList= (List<Event>) msg.obj;
                    mAdapter = new EventAdapter(EventActivity.this,R.layout.event_activity,eventList);
                    mAdapter.notifyDataSetChanged();
                    listView.setAdapter(mAdapter);

                    progressDialog.dismiss();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Event event = eventList.get(position);

                            Toast.makeText(EventActivity.this, event.getTitle(), Toast.LENGTH_SHORT).show();

                            Intent intent1 = new Intent(EventActivity.this, MainActivity.class);
                            intent1.putExtra("eventId", event.getEventId());

                            Log.d("III",event.getEventId());

                            startActivity(intent1);
                        }
                    });

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        listView= (ListView) findViewById(R.id.list_event);
        weatherImage= (ImageView) findViewById(R.id.weather_image);
        temp= (TextView) findViewById(R.id.temp);
        time= (TextView) findViewById(R.id.time);
        weather= (TextView) findViewById(R.id.weather);

        if(progressDialog==null){

            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("加载中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        setTitle(name);

        ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener=new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                switch (tab.getPosition()){
                    case 0:
                        type="all";
                        sendInfo(type);
                        break;
                    case 1:
                        type="music";
                        sendInfo(type);
                        break;
                    case 2:
                        type="film";
                        sendInfo(type);
                        break;
                    case 3:
                        type="drama";
                        sendInfo(type);
                        break;
                    case 4:
                        type="salon";
                        sendInfo(type);
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };



        ActionBar.Tab tab=actionBar.newTab()
                .setText("所有").setTabListener(tabListener);
        actionBar.addTab(tab);
        ActionBar.Tab tab1=actionBar.newTab()
                .setText("音乐").setTabListener(tabListener);
        actionBar.addTab(tab1);
        ActionBar.Tab tab2=actionBar.newTab()
                .setText("电影").setTabListener(tabListener);
        actionBar.addTab(tab2);
        ActionBar.Tab tab3=actionBar.newTab()
                .setText("戏剧").setTabListener(tabListener);
        actionBar.addTab(tab3);
        ActionBar.Tab tab4=actionBar.newTab()
                .setText("讲座").setTabListener(tabListener);
        actionBar.addTab(tab4);



        try {
            urlEncode = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String website="http://v.juhe.cn/weather/index?cityname="+urlEncode+"&key=d1ee4ea50af6370e827796e80d71f72f";
        HttpUtil.sendHttpRequest(website, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.parseJson(EventActivity.this,response);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.event,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                Intent intent=new Intent(EventActivity.this,SaveActivity.class);
                startActivity(intent);
                break;
        }


        return true;
    }



    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray("events");
            List<Event> eventList1=new ArrayList<Event>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);

                String eventId = json.getString("id");
                String title = json.getString("title");
                String startTime = json.getString("begin_time");
                String endTime = json.getString("end_time");


                Event event = new Event();
                event.setEventId(eventId);
                event.setStartTime(startTime);
                event.setEndTime(endTime);
                event.setTitle(title);

                eventList1.add(event);
            }
            Message message=new Message();
            message.what=DDD;
            message.obj=eventList1;
            handler.sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void sendInfo(String type){
        String website = "https://api.douban.com/v2/event/list?loc=" + id + "&day_type=future&type="+type;

        HttpUtil.sendHttpRequest(website, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseJSONWithJSONObject(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void showWeather(){
        SharedPreferences preferences=getSharedPreferences("weatherInfo", MODE_PRIVATE);
        String data1=preferences.getString("data", "");
        String temp1=preferences.getString("temp","");
        String weather1=preferences.getString("weather","");
        String weatherif=preferences.getString("weatherif","");

        weather.setText(weatherif);
        temp.setText(temp1);
        time.setText(data1);

        switch (weather1){
            case "00":
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case "01":
                weatherImage.setImageResource(R.drawable.cloudy);
                break;
            case "02":
                weatherImage.setImageResource(R.drawable.yintian);
                break;
            case "03":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "04":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "05":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "06":
                weatherImage.setImageResource(R.drawable.yujiaxue);
                break;
            case "07":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "08":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "09":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "10":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "11":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "12":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "13":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "14":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "15":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "16":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "17":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "18":
                weatherImage.setImageResource(R.drawable.cloudy);
                break;
            case "19":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "20":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "21":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "22":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "23":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "24":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "25":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            case "26":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "27":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "28":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "29":
                weatherImage.setImageResource(R.drawable.wu);
                break;
            case "30":
                weatherImage.setImageResource(R.drawable.wu);
                break;
            case "31":
                weatherImage.setImageResource(R.drawable.wu);
                break;
            case "53":
                weatherImage.setImageResource(R.drawable.wu);
                break;
            default:
                weatherImage.setImageResource(R.drawable.sunny);




        }
    }

}
