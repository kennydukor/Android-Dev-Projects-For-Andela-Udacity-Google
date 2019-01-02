package com.kenechi.journalapp.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kenechi.journalapp.R;
import com.kenechi.journalapp.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_confirm_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    private ProgressBar reg_progress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        reg_email_field = (EditText) findViewById(R.id.reg_email);
        reg_pass_field = (EditText) findViewById(R.id.reg_pass);
        reg_confirm_pass_field = (EditText) findViewById(R.id.reg_confirm_pass);
        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_login_btn = (Button) findViewById(R.id.reg_login_btn);
        reg_progress = (ProgressBar) findViewById(R.id.reg_progress);

        //
        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLogin();

            }
            //Anonymous inner class
            private void sendToLogin() {
                //send user to Login Activity
                Intent loginIntent = new Intent(SignUpActivity.this, SignUpActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        //Listener to start Registration Process
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = reg_email_field.getText().toString();
                String pass = reg_pass_field.getText().toString();
                String confirm_pass = reg_confirm_pass_field.getText().toString();

                //check if email, password and confirm password fields are empty
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(confirm_pass)){
                    //check if password and confirm password matches
                    if (pass.equals(confirm_pass))
                    {
                        reg_progress.setVisibility(View.VISIBLE);

                        //Register user with Email and password
                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //if registration task is Successful
                                if (task.isSuccessful()){

                                    //send new user to Setup Activity
                                    Intent setupIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(setupIntent);
                                    //finish prevents the back button from taking the user back to Register Activity
                                    finish();

                                    //sendToMain();

                                }else{
                                    //registration task is not Successful
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(SignUpActivity.this, "Error : "  + errorMessage ,Toast.LENGTH_LONG).show();

                                }

                                //make the progress bar invisible after Registration is successful or not
                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });


                    }else {
                        //password and confirm password does'nt match
                        Toast.makeText(SignUpActivity.this, "Confirm password and Password field doesn't match", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){

            sendToMain();

        }
    }

    //This method start Main Activity
    private void sendToMain() {
        Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}
