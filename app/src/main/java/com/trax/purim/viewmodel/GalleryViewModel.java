package com.trax.purim.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asisayag on 17/02/2018.
 */

public class GalleryViewModel extends AndroidViewModel {
    private DatabaseReference imagesRef;
    private MutableLiveData<List<String>> images;

    public GalleryViewModel(@NonNull Application application){
        super(application);
        imagesRef = FirebaseDatabase.getInstance("https://traxpurim.firebaseio.com/").getReference("uploaded_images");
        images = new MutableLiveData<>();
        imagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.postValue(toImages(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(application.getBaseContext(), "gallery images update failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> toImages(DataSnapshot dataSnapshot) {
        HashMap<String,String> map = (HashMap<String,String>) dataSnapshot.getValue();
        List<String> res = new ArrayList<>(map.values());
        return res;
    }

    public MutableLiveData<List<String>> getImageURLs(){
        return images;
    }
}
