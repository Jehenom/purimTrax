package com.trax.purim;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.trax.purim.viewmodel.MainViewModel;
import com.wooplr.spotlight.SpotlightView;


public class MainActivity extends AppCompatActivity {

    MainViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewmodel = ViewModelProviders.of(this).get(MainViewModel.class);
        findViewById(R.id.home_vote_btn).setOnClickListener(v-> openVoting());
        findViewById(R.id.home_take_photo_btn).setOnClickListener(this::openCamera);
        initActionBar();
        handleSpotlights();
    }

    private void initActionBar() {
        ActionBar toolBar = getSupportActionBar();
        toolBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e9302b")));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent openActivityIntent = new Intent(this, GalleryActivity.class);
        startActivity(openActivityIntent);
        return super.onOptionsItemSelected(item);
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

    private void handleSpotlights() {
        getSpotlightBuilder(findViewById(R.id.home_vote_btn), "Click here to vote \nfor your favorite costume")
                .setListener(s -> getSpotlightBuilder(findViewById(R.id.home_take_photo_btn), "Click here to take & send pictures. \nPics will be shared with everyone later on.").show()).show();
    }

    private SpotlightView.Builder getSpotlightBuilder(Button view, String message){
        return new SpotlightView.Builder(this)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(false)
                .fadeinTextDuration(200)
                .headingTvColor(Color.parseColor("#fee4ff"))
                .headingTvSize(32)
                .headingTvText(view.getText())
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(message)
                .maskColor(Color.parseColor("#99000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(String.valueOf(view.getId()));
    }
}
