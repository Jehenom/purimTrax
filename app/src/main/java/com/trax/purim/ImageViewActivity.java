package com.trax.purim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Nadir Elmakias on 1/27/2018.
 */

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent getStringIntent = getIntent();
        Bundle bundle = getStringIntent.getExtras();
        String filePath = bundle.getString("image_file");
        Uri uri = Uri.parse("file://"+filePath);
        SimpleDraweeView draweeView =  findViewById(R.id.imageView);
        draweeView.setImageURI(uri);
    }
}


