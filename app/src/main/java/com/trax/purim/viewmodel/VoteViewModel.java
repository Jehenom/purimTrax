package com.trax.purim.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.trax.purim.model.VoteOption;

import java.util.Arrays;
import java.util.List;

/**
 * Created by asisayag on 27/01/2018.
 */

public class VoteViewModel extends AndroidViewModel {

    DatabaseReference voteOptionsRef;
    DatabaseReference selectedOptionRef;
    MutableLiveData<List<VoteOption>> options;
    int selectedOption = -1;


    public VoteViewModel(@NonNull Application application) {
        super(application);
        voteOptionsRef = FirebaseDatabase.getInstance("https://traxpurim.firebaseio.com/").getReference("votes_options");
        options = new MutableLiveData<>();
        voteOptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                options.postValue(toOptions(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication().getBaseContext(), "vote_options update failed", Toast.LENGTH_LONG).show();
            }
        });

        selectedOptionRef = FirebaseDatabase.getInstance("https://traxpurim.firebaseio.com/").getReference("votes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        selectedOptionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = (String)dataSnapshot.getValue();
                selectedOption = val!=null ? Integer.parseInt(val) : -1;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplication().getBaseContext(), "votes update failed", Toast.LENGTH_LONG).show();
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
        selectedOptionRef.setValue(String.valueOf(voteId));
    }

    public int getVoted(){
        return selectedOption;
    }
}
