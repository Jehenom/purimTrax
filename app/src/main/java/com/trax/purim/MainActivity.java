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
        setContentView(R.layout.activity_home);
        viewmodel = ViewModelProviders.of(this).get(MainViewModel.class);
        findViewById(R.id.home_vote_btn).setOnClickListener(v-> openVoting());
        findViewById(R.id.home_take_photo_btn).setOnClickListener(v-> openCamera(v));

        getSupportActionBar().hide();
    }

    private void openVoting() {
        Intent openActivityIntent = new Intent(this, VoteActivity.class);
        startActivity(openActivityIntent);
    }

    public void openCamera(View view) {
        Intent openCameraViewIntent = new Intent(this, CameraActivity.class);
        startActivity(openCameraViewIntent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
