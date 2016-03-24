package ua.kyselov.android.runtimer;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;



public class StopWatchActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunnig;
    TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            running = savedInstanceState.getBoolean("runningState");
            seconds = savedInstanceState.getInt("secondsState");
            wasRunnig = savedInstanceState.getBoolean("wasRunningState");
        }
        setContentView(R.layout.activity_stop_watch);
        timeView = (TextView)findViewById(R.id.timerTxtV);
        runTimer();
    }

    public void onTimerStart(View view) {
         running = true;
    }

    public void onTimerStop(View view) {
        wasRunnig = running;
        running = false;
    }

    public void onTimerReset(View view) {
        wasRunnig = false;
        running = false;
        seconds = 0;
    }

    private void runTimer(){
    final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean("runningState", running);
        savedInstanceState.putInt("secondsState", seconds);
        savedInstanceState.putBoolean("wasRunningState", running);
    }

    @Override
    public void onStart(){
        super.onStart();
        if(wasRunnig){
            running = true;
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        wasRunnig = running;
        running = false;
    }
}

