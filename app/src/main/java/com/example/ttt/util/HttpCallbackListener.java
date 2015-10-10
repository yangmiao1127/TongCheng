package com.example.ttt.util;

/**
 * Created by 1 on 2015/9/30.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
