package com.trax.purim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by Nadir Elmakias on 1/27/2018.
 */

public class ImageViewActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private String filePath;
    private ImageButton ShareButton;
    private ImageButton DeleteButton;
    private ImageButton SaveButton;
    private ImageButton UploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent getStringIntent = getIntent();
        Bundle bundle = getStringIntent.getExtras();
        filePath = bundle.getString("image_file");
        Uri photoUri = Uri.parse("file://"+filePath);
        SimpleDraweeView draweeView =  findViewById(R.id.imageView);
        draweeView.setImageURI(photoUri);

        ShareButton = findViewById(R.id.ShareButton);
        ShareButton.setOnClickListener(v -> {
            Uri photoUri1 = Uri.parse("file://"+filePath);
            dispatchShareIntent(photoUri1);
        });

        DeleteButton = findViewById(R.id.DeleteButton);
        DeleteButton.setOnClickListener(v -> deleteFile());

        SaveButton = findViewById(R.id.SaveButton);

        UploadButton = findViewById(R.id.UploadButton);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        UploadButton.setOnClickListener(v -> uploadToFirebase(ImageViewActivity.this));

        getSupportActionBar().hide();

    }

    private void deleteFile() {
        File file = new File(filePath);
        if(file.delete()){
            Toast.makeText(ImageViewActivity.this,
                    "file deleted", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void dispatchShareIntent(Uri photoUri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

    public void uploadToFirebase(final Context context) {
        File currFile = new File(filePath);
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
    }
}


