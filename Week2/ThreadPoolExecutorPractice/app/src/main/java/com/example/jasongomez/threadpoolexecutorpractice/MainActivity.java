package com.example.jasongomez.threadpoolexecutorpractice;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    private TextView textView1, textView2, textView3, textView4;
    private UiHandler uiHandler;
    private CustomThreadPoolManager customThreadPoolManager;
    private Button rxButton;
    private Scheduler scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.tvThread1);
        textView2 = findViewById(R.id.tvThread2);
        textView3 = findViewById(R.id.tvThread3);
        textView4 = findViewById(R.id.tvThread4);
        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        progressBar3 = findViewById(R.id.progress3);
        progressBar4 = findViewById(R.id.progress4);
        rxButton = findViewById(R.id.btnAddCallableRx);
        scheduler = Schedulers.from(Executors.newFixedThreadPool(4));

        uiHandler = new UiHandler(getMainLooper(),
                textView1,
                textView2,
                textView3,
                textView4,
                progressBar1,
                progressBar2,
                progressBar3,
                progressBar4);

        customThreadPoolManager = CustomThreadPoolManager.getInstance();
        customThreadPoolManager.setHandler(uiHandler);
    }

    public void addCallable(View view) {
        customThreadPoolManager.addRunnable();
    }

    public void addCallableRx(View view) {
        Observable.create(subscriber ->
                rxButton.setOnClickListener(button -> subscriber.onNext(view)))
                .observeOn(scheduler)
                .flatMap(v -> Observable.just(1, 2, 3, 4))
                .map(i -> {
                    Thread.sleep(1000);
                    String message = "Random int: " + i + "\nThread "
                            + String.valueOf(Thread.currentThread().getId()) + "\nCompleted";
                    Bundle bundle = new Bundle();
                    bundle.putString("message", message);
                    bundle.putInt("progress", i);
                    bundle.putInt("whichThread", (int) (Thread.currentThread().getId() % 4 + 1));
                    return bundle;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bundle>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bundle bundle) {
                        String message = bundle.getString("message");
                        int i = bundle.getInt("progress");
                        int whichThread = bundle.getInt("whichThread");

                        switch (whichThread) {
                            case 1:
                                progressBar1.setProgress(i);
                                textView1.setText(message);
                                break;

                            case 2:
                                progressBar2.setProgress(i);
                                textView2.setText(message);
                                break;

                            case 3:
                                progressBar3.setProgress(i);
                                textView3.setText(message);
                                break;

                            case 4:
                                progressBar4.setProgress(i);
                                textView4.setText(message);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static class UiHandler extends Handler {

        private WeakReference<TextView> tvFirstThread, tvSecondThread, tvThirdThread, tvFourthThread;
        private WeakReference<ProgressBar> firstProgress, secondProgress, thirdProgress, fourthProgress;

        public UiHandler(Looper looper,
                         TextView tvFirstThread,
                         TextView tvSecondThread,
                         TextView tvThirdThread,
                         TextView tvFourthThread,
                         ProgressBar firstProgress,
                         ProgressBar secondProgress,
                         ProgressBar thirdProgress,
                         ProgressBar fourthProgress) {

            super(looper);
            this.tvFirstThread = new WeakReference<>(tvFirstThread);
            this.tvSecondThread = new WeakReference<>(tvSecondThread);
            this.tvThirdThread = new WeakReference<>(tvThirdThread);
            this.tvFourthThread = new WeakReference<>(tvFourthThread);
            this.firstProgress = new WeakReference<>(firstProgress);
            this.secondProgress = new WeakReference<>(secondProgress);
            this.thirdProgress = new WeakReference<>(thirdProgress);
            this.fourthProgress = new WeakReference<>(fourthProgress);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    firstProgress.get().setProgress(msg.getData().getInt("int"));
                    tvFirstThread.get().setText(msg.getData().getString("message"));
                    break;

                case 2:
                    secondProgress.get().setProgress(msg.getData().getInt("int"));
                    tvSecondThread.get().setText(msg.getData().getString("message"));
                    break;

                case 3:
                    thirdProgress.get().setProgress(msg.getData().getInt("int"));
                    tvThirdThread.get().setText(msg.getData().getString("message"));
                    break;

                case 4:
                    fourthProgress.get().setProgress(msg.getData().getInt("int"));
                    tvFourthThread.get().setText(msg.getData().getString("message"));
                    break;
            }
        }
    }
}
