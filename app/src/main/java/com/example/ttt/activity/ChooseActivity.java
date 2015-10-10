package com.example.ttt.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ttt.R;
import com.example.ttt.adapter.CityAdapter;
import com.example.ttt.model.City;
import com.example.ttt.util.HttpCallbackListener;
import com.example.ttt.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2015/9/30.
 */
public class ChooseActivity extends Activity{


    private ListView cityListView;
    private ProgressDialog progressDialog;
    private static final int CCC=4;
    private List<City> mList=new ArrayList<City>();
    private CityAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CCC:
                    mList= (List<City>) msg.obj;
                    adapter=new CityAdapter(ChooseActivity.this,R.layout.citystyle,mList);
                    cityListView.setAdapter(adapter);
                    cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            City city = mList.get(position);

                            Toast.makeText(ChooseActivity.this, city.getCityName(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ChooseActivity.this, EventActivity.class);
                            intent.putExtra("id", city.getCityId());
                            intent.putExtra("name", city.getCityName());
                            startActivity(intent);
                        }
                    });


                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        cityListView= (ListView) findViewById(R.id.city_list);

        setTitle("选择城市");

        String webSite="https://api.douban.com/v2/loc/list";

        if(progressDialog==null){

            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("加载中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        HttpUtil.sendHttpRequest(webSite, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                progressDialog.dismiss();
                parseJSONWithJSONObjects(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void parseJSONWithJSONObjects(String jsonData){
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray array=jsonObject.getJSONArray("locs");
            List<City> cityList=new ArrayList<City>();
            for (int i = 0; i <array.length() ; i++) {
                JSONObject json=array.getJSONObject(i);
                String id=json.getString("id");
                String name=json.getString("name");
                City city=new City();
                city.setCityName(name);
                city.setCityId(id);
                cityList.add(city);

            }
            Message message=new Message();
            message.what=CCC;
            message.obj=cityList;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
