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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kenechi.journalapp.main.MainActivity;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private EditText mLoginEmailText;
    private EditText mLoginPassText;
    private Button mLoginBtn;
    private Button mLoginRegBtn;

    private SignInButton mSignInButton;
    private ProgressBar mLoginProgress;

    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        //Initialize widgets
        mLoginEmailText = (EditText) findViewById(R.id.reg_email);
        mLoginPassText = (EditText) findViewById(R.id.reg_confirm_pass);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginRegBtn = (Button) findViewById(R.id.login_reg_btn);
        mSignInButton = findViewById(R.id.google_sign_in);
        mLoginProgress = (ProgressBar) findViewById(R.id.login_progress);

        //Listener to start Login
        mLoginBtn.setOnClickListener(this);
        //Listener to start sign in
        mSignInButton.setOnClickListener(this);
        //Listener to start Register Activity
        mLoginRegBtn.setOnClickListener(this);


    }

    private void login(){
        String loginEmail = mLoginEmailText.getText().toString();
        String loginPass = mLoginPassText.getText().toString();

        //If email and password fields are not empty,
        if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass) ){

            //Make the progress bar visible
            mLoginProgress.setVisibility(View.VISIBLE);
            //firebase sign in with email and password
            mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //If login task was successful
                    if (task.isSuccessful()){
                        //start Main Activity
                        sendToMain();

                    }else {
                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(SignInActivity.this,  "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                    }
                    //Make progress bar invisible,
                    //whether login was successful or not
                    mLoginProgress.setVisibility(View.INVISIBLE);
                }
            });


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Get the current user as a firebase object
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //If user is logged in
        if (currentUser !=null ){
            //start Main Activity
            sendToMain();

        }

    }

    //This method start Main Activity
    private void sendToMain() {
        Intent mainActivityIntent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.google_sign_in:
                googleSignIn();
                break;
            case R.id.login_btn:
                login();
                break;

            case R.id.login_reg_btn:
                Intent regIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(regIntent);
                break;

        }

    }

    private void googleSignIn() {
        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(googleSignInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                //GoogleSignInAccount account = result.getSignInAccount();
                //String displayName =  account.getDisplayName();
                sendToMain();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(SignInActivity.this, "Pls sign in", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
