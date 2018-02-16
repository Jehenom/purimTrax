package com.trax.purim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    private static final long DELAY_BEFORE_CLOSE = 3000;
    Handler handler;
    Runnable exitActivityRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        getSupportActionBar().hide();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }


    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler();
        exitActivityRunnable = () -> startActivity(new Intent(ThankYouActivity.this, MainActivity.class));
        handler.postDelayed(exitActivityRunnable, DELAY_BEFORE_CLOSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(exitActivityRunnable);
    }
}
