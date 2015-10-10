package com.example.ttt.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.ttt.R;
import com.example.ttt.model.Event;
import com.example.ttt.service.AutoService;
import com.example.ttt.util.HttpCallbackListener;
import com.example.ttt.util.HttpUtil;
import com.example.ttt.util.Utility;
/**
 * Created by 1 on 2015/9/30.
 */
public class MainActivity extends Activity{

    private ImageView imageView;
    private TextView title;
    private TextView participant;
    private TextView wish;
    private TextView content;
    private String title1;
    private String participant1;
    private String wish1;
    private String content1;
    private Button map;
    private String userId;
    private String userName;
    private String userImage;
    private ProgressDialog progressDialog;

    private Button look;

    private String latitude;
    private String longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        participant = (TextView) findViewById(R.id.participant);
        wish = (TextView) findViewById(R.id.wish);
        content = (TextView) findViewById(R.id.content);
        map= (Button) findViewById(R.id.map);
        look= (Button) findViewById(R.id.look);
        if(progressDialog==null){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("加载中");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        }

        Intent intent = getIntent();
        String eventId = intent.getStringExtra("eventId");
        String website = "https://api.douban.com/v2/event/" + eventId;

        HttpUtil.sendHttpRequest(website, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleEvent(MainActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showInfo();
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });

        setTitle("活动详情");




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.share:
                Toast.makeText(MainActivity.this,"分享",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String share=preferences.getString("title", "");
                String address=preferences.getString("address", "");
                String beginTime=preferences.getString("beginTime","");
                String content="快和我一起参加"+share+"活动吧,"+"地址："+address+",时间："+beginTime;
                Intent sendIntent=new Intent(Intent.ACTION_SENDTO, Uri.parse("sms: "));
                sendIntent.putExtra("sms_body", content);
                startActivity(sendIntent);
                break;
            case R.id.add :
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
                String title=preferences1.getString("title", "");
                String startTime=preferences1.getString("address", "");
                String endTime=preferences1.getString("beginTime", "");

                Intent intent1=new Intent(MainActivity.this, AutoService.class);
                startService(intent1);

                Event event=new Event();
                event.setEventId("1");
                event.setTitle(title);
                event.setStartTime(startTime);
                event.setEndTime(endTime);
                event.save();

                if(event.save()){
                    Toast.makeText(MainActivity.this,"已添加至收藏",Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
   private void showInfo(){
       SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
       String imageId=preferences.getString("imageId","");
       title1=preferences.getString("title", "");
       participant1=preferences.getInt("participant_count", 0) + "人参加";
       content1=preferences.getString("content", "");
       wish1=preferences.getInt("wisher_count", 0) + "人感兴趣";

       userName=preferences.getString("userName","");
       userId=preferences.getString("userId","");
       userImage=preferences.getString("userImage","");

       look.setText(userName);
       title.setText(title1);
       participant.setText(participant1);
       content.setText(content1);
       wish.setText(wish1);
       String url=imageId;
       RequestQueue queue=Volley.newRequestQueue(this);
       ImageLoader imageLoader=new ImageLoader(queue, new ImageLoader.ImageCache() {
           @Override
           public Bitmap getBitmap(String s) {
               return null;
           }

           @Override
           public void putBitmap(String s, Bitmap bitmap) {

           }
       });
       ImageLoader.ImageListener listener=ImageLoader.getImageListener(imageView,R.drawable.qwe,R.drawable.qwe);
       imageLoader.get(url,listener);

       String geo=preferences.getString("geo", "");
       String[] array=geo.split(" ");
       latitude=array[0];
       longitude=array[1];



       map.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, MapActivity.class);
               intent.putExtra("name",title1);
               intent.putExtra("latitude", latitude);
               intent.putExtra("longitude", longitude);
               startActivity(intent);
           }
       });
       look.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
               intent.putExtra("userName",userName);
               intent.putExtra("userId",userId);
               intent.putExtra("userImage",userImage);
               startActivity(intent);
           }
       });

    }



}
