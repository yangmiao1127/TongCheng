package com.example.ttt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 1 on 2015/9/30.
 */
public class Utility {
    public static void handleEvent(Context context, String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String title=jsonObject.getString("title");
            Integer participant_count = jsonObject.getInt("participant_count");
            Integer wisher_count = jsonObject.getInt("wisher_count");
            String content=jsonObject.getString("content");
            String address=jsonObject.getString("address");
            String imageId=jsonObject.getString("image");
            String beginTime=jsonObject.getString("begin_time");
            String geo=jsonObject.getString("geo");

            JSONObject jsonObject1=jsonObject.getJSONObject("owner");
            String userId=jsonObject1.getString("id");
            String userName=jsonObject1.getString("name");
            String userImage=jsonObject1.getString("avatar");

            Log.d("userId",userId);
            Log.d("userName",userName);
            Log.d("userImage",userImage);

            saveInfo(context, title, participant_count, wisher_count, content, address, imageId, beginTime, geo
                    , userId, userImage, userName);
            } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void saveInfo(Context context,String title,Integer participant_count,Integer wisher_count,String content,
                                String address,String imageId,String beginTime,String geo,String userId,String userImage
    ,String userName){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.putString("title", title);
        editor.putInt("participant_count", participant_count);
        editor.putInt("wisher_count",wisher_count);
        editor.putString("content", content);
        editor.putString("address",address);
        editor.putString("imageId",imageId);
        editor.putString("beginTime",beginTime);
        editor.putString("geo",geo);
        editor.putString("userImage",userId);
        editor.putString("userId",userImage);
        editor.putString("userName",userName);
        editor.commit();
    }
    public static void parseJson(Context context, String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject jsonObject1 =jsonObject.getJSONObject("result");
            JSONObject jsonObject2=jsonObject1.getJSONObject("today");
            String date=jsonObject2.getString("date_y");
            String temperature=jsonObject2.getString("temperature");
            String weatherif=jsonObject2.getString("weather");
            JSONObject jsonObject3=jsonObject2.getJSONObject("weather_id");
            String weather=jsonObject3.getString("fa");
            Log.d("99993",date);
            Log.d("99994",temperature);
            Log.d("99995",weather);
            saveIf(context, date, temperature,weatherif, weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void saveIf(Context context,String data,String temp,String weatherif,String weather){
        SharedPreferences.Editor editor=context.getSharedPreferences("weatherInfo",Context.MODE_PRIVATE).edit();
        editor.putString("data",data);
        editor.putString("temp",temp);
        editor.putString("weather",weather);
        editor.putString("weatherif",weatherif);
        editor.commit();
    }
}