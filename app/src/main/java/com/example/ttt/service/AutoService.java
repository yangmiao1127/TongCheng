package com.example.ttt.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ttt.R;
import com.example.ttt.activity.SaveActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 1 on 2015/10/4.
 */
public class AutoService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                updata();
            }
        },30,2*60*60*1000);

        return super.onStartCommand(intent, flags, startId);
    }
    private void updata(){
        NotificationManager manager= (NotificationManager) AutoService.this.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(AutoService.this);
        Intent notificationIntent=new Intent(AutoService.this, SaveActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(AutoService.this,0,notificationIntent,0);
        builder.setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_notifications_on_blue_400_18dp)
                .setTicker("查看您收藏的同城活动").setContentText("查看收藏活动").setContentInfo("查看活动").setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification=builder.build();
        manager.notify((int)System.currentTimeMillis(),notification);
    }
}
