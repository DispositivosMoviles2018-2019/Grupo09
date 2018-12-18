package ec.edu.uce.appperiodtask1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class TimingActivity extends AppCompatActivity {

    TextView mClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClock = new TextView(this);
        setContentView(mClock);
    }

    private Handler mHandler = new Handler();
    private Runnable timerTask = new Runnable() {

        @Override
        public void run() {
            Calendar now = Calendar.getInstance();
            mClock.setText(String.format("%02d:%02d:%02d", now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND)) );

            mHandler.postDelayed(timerTask,1000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(timerTask);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(timerTask);
    }
}
