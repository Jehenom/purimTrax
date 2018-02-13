package com.trax.purim.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.trax.purim.LoginActivity;

/**
 * Created by asisayag on 13/02/2018.
 */

public class LoginViewModel extends ViewModel {

    public void login(Activity context, FirebaseAuth mAuth, String userName, final LoginActivity.OnLoginResult callback){
        mAuth.signInWithEmailAndPassword(userName, "12345678")
                .addOnCompleteListener(context, task -> callback.onLoginDone(task.isSuccessful()));

    }
}
