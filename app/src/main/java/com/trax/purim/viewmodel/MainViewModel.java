package com.trax.purim.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asisayag on 21/01/2018.
 */

public class MainViewModel extends ViewModel {
    private StorageReference mStorageRef;

    private String mCurrentPhotoPath;
    public MainViewModel(){
        mStorageRef = FirebaseStorage.getInstance().getReference();

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

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath= image.getAbsolutePath();
        return image;
    }

    public void uploadToFirebase(final Context context){
        File currFile = new File(mCurrentPhotoPath);
        Uri file = Uri.fromFile(currFile);
        StorageReference tempRef = mStorageRef.child("images/"+currFile.getName());

        tempRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get a URL to the uploaded content
                    Toast.makeText(context,
                            "upload was successful" , Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(context,
                            "upload was not successful: "+exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
