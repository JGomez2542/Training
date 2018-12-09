package com.example.user.multithreadingandroid;

import android.os.Handler;
import android.os.Message;

/**
 * Created by singh on 9/7/17.
 */

public class CallbackHandler implements Handler.Callback {




    @Override
    public boolean handleMessage(Message message) {

        System.out.println("CallbackHandler" + message.getData().getString("KEY_DATA"));
        return false;
    }
}
