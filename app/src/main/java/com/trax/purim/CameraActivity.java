package com.trax.purim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        cameraView.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        cameraView.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER); // Tap to focus!
        takePictureButton.setOnClickListener(v -> takePicture(v));
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                String fileName = saveImage(picture);
                openViewer(fileName);
            }
        });
        getSupportActionBar().hide();

    }

    private String saveImage(byte[] picture) {
        File imageFile;
        try {
            imageFile = createImageFile(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bos.write(picture);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return imageFile.getAbsolutePath();
    }

    private void openViewer(String fileName){
        Intent openImageViewIntent = new Intent(this, ImageViewActivity.class);
        openImageViewIntent.putExtra("image_file", fileName);
        startActivity(openImageViewIntent);
    }

    public File createImageFile(File storageDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
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
