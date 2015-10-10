package com.example.ttt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.ttt.R;


/**
 * Created by 1 on 2015/10/2.
 */
public class MapActivity extends Activity {

    private MapView mapView;
    private BaiduMap baiduMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        Double latitude= Double.valueOf(intent.getStringExtra("latitude"));
        Double longitude= Double.valueOf(intent.getStringExtra("longitude"));
        String name=intent.getStringExtra("name");
        setTitle(name);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_layout);
        mapView= (MapView) findViewById(R.id.map_view);

        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);


        LatLng latLng = new LatLng(latitude,longitude);//设置位置坐标
        MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(update);

        update= MapStatusUpdateFactory.zoomTo(16f);//设置缩放级别
        baiduMap.animateMapStatus(update);

        MyLocationData.Builder builder=new MyLocationData.Builder();
        builder.latitude(latitude);
        builder.longitude(longitude);
        MyLocationData locationData=builder.build();
        baiduMap.setMyLocationData(locationData);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        /*baiduMap.setMyLocationEnabled(false);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
