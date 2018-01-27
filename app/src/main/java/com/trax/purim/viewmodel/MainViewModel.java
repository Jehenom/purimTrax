package com.trax.purim.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by asisayag on 21/01/2018.
 */

public class MainViewModel extends ViewModel {

    private StorageReference mStorageRef;
    private String mCurrentPhotoPath;

    public MainViewModel(){
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    public void uploadToFirebase(final Context context) {
        File currFile = new File(mCurrentPhotoPath);
        Uri file = Uri.fromFile(currFile);
        StorageReference tempRef = mStorageRef.child("images/" + currFile.getName());

        tempRef.putFile(file)
                .addOnSuccessListener(taskSnapshot -> Toast.makeText(context,
                    "upload was successful", Toast.LENGTH_LONG).show()
                ).addOnFailureListener(exception -> {
                // Handle unsuccessful uploads
                    Toast.makeText(context,
                        "upload was not successful: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
                }
        );
    }}




