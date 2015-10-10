package com.example.ttt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ttt.service.AutoService;

/**
 * Created by 1 on 2015/10/3.
 */
public class StartReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, AutoService.class);
        context.startService(intent1);

    }
}
