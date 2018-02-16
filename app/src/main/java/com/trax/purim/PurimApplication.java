package com.trax.purim;

/**
 * Created by Nadir Elmakias on 1/27/2018.
 */

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class PurimApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}