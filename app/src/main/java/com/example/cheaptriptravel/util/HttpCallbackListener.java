package com.example.cheaptriptravel.util;

/**
 * Created by Administrator on 14/2/2018.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}