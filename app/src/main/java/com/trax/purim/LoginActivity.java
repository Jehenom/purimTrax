package com.trax.purim;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trax.purim.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel viewmodel;
    EditText textBox;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //close app if we are coming back from the main activity
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel.class);
        textBox = findViewById(R.id.login_email_edit_text);
        findViewById(R.id.login_button).setOnClickListener(v->login());
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) openHomeActivity();
    }

    private void login(){
        viewmodel.login(this, mAuth, textBox.getText().toString(), new OnLoginResult(){
            @Override
            public void onLoginDone(boolean successfulLogin){
                if (!successfulLogin){
                    Toast.makeText(LoginActivity.this,"Login failed", Toast.LENGTH_LONG).show();
                }
                else {
                    openHomeActivity();
                }
            }
        });

    }

    private void openHomeActivity() {
        startActivity(new Intent(new Intent(this, MainActivity.class)));
    }

    public abstract class OnLoginResult{
        public abstract void onLoginDone(boolean successfulLogin);
    }
}
