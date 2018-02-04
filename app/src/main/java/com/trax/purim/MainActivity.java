package com.trax.purim;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.trax.purim.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewmodel = ViewModelProviders.of(this).get(MainViewModel.class);
        getSupportActionBar().hide();
    }

    /** Called when the user touches the button */
    public void openCamera(View view) {
        // open camera in response to button click
        Intent openCameraViewIntent = new Intent(this, CameraActivity.class);
        startActivity(openCameraViewIntent);
    }
}
