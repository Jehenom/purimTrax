package com.trax.purim.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trax.purim.model.VoteOption;
import com.trax.purim.model.VoteOptionsModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by asisayag on 27/01/2018.
 */

public class VoteViewModel extends AndroidViewModel {

    DatabaseReference ref;
    MutableLiveData<List<VoteOption>> options;


    public VoteViewModel(@NonNull Application application) {
        super(application);
        ref = FirebaseDatabase.getInstance("https://traxpurim.firebaseio.com/").getReference("votes_options");
        options = new MutableLiveData<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                options.postValue(toOptions(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication().getBaseContext(), "Failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    public int getNumberOfPictures(){
        return options.getValue()!=null ? options.getValue().size():0;
    }

    public VoteOption getOption(int id){

        return options.getValue()!=null ? options.getValue().get(id):null;
    }


    public MutableLiveData<List<VoteOption>> getOptions() {
        return options;
    }

    private List<VoteOption> toOptions(DataSnapshot data) {
        String json = new Gson().toJson(data.getValue());
        VoteOption[] f = new Gson().fromJson(json, VoteOption[].class);

        return Arrays.asList(f);
    }

    public void vote(int voteId) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traxpurim.firebaseio.com/").getReference("votes");
        ref.child(uid).setValue(voteId);
    }
}
