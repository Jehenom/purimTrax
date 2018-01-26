package com.trax.purim;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.trax.purim.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    MainViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewmodel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    /** Called when the user touches the button */
    public void openCamera(View view) {
        // open camera in response to button click
        Intent openCameraViewIntent = new Intent(this, CameraActivity.class);
        startActivity(openCameraViewIntent);
    }


//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = viewmodel.createImageFile(getExternalFilesDir
//                        (Environment.DIRECTORY_PICTURES));
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.trax.purim.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Toast.makeText(this,"got an image", Toast.LENGTH_LONG).show();
//            viewmodel.uploadToFirebase(this);
//        }
//    }


}
