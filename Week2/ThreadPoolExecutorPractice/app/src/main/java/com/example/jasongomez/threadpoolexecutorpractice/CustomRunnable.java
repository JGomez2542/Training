package com.example.jasongomez.threadpoolexecutorpractice;

import android.os.Bundle;
import android.os.Message;

import java.lang.ref.WeakReference;

public class CustomRunnable implements Runnable {

    private WeakReference<CustomThreadPoolManager> customThreadPoolManagerWeakReference;

    public CustomRunnable(CustomThreadPoolManager customThreadPoolManagerWeakReference) {
        this.customThreadPoolManagerWeakReference = new WeakReference<>(customThreadPoolManagerWeakReference);
    }

    @Override
    public void run() {
        try {

            for (int i = 1; i < 5; i++) {
                Thread.sleep(1000);
                String message = "Random int: " + i + "\nThread "
                        + String.valueOf(Thread.currentThread().getId()) + "\nCompleted";

                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                bundle.putInt("int", i);
                Message message1 = new Message();
                message1.setData(bundle);
                message1.what = (int) (Thread.currentThread().getId() % 4 + 1);
                customThreadPoolManagerWeakReference.get().postToUiThread(message1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
