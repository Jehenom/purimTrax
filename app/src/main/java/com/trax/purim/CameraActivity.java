package com.trax.purim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.trax.purim.viewmodel.MainViewModel;

/**
 * Created by Nadir Elmakias on 1/24/2018.
 */

public class CameraActivity extends AppCompatActivity{

    private CameraView cameraView;
    private ImageButton takePictureButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraView = findViewById(R.id.camera);
        takePictureButton = findViewById(R.id.ib_take_picture_button);
        takePictureButton.setOnClickListener(v -> takePicture(v));
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                // CameraUtils will read EXIF orientation for you, in a worker thread.
//                CameraUtils.decodeBitmap(picture, Bitmap::prepareToDraw);
                Toast.makeText(getApplicationContext(), "picture taken",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    public void takePicture(View view){
       cameraView.capturePicture();
    }
}
