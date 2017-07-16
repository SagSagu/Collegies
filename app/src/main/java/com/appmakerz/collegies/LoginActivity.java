package com.appmakerz.collegies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by daksha on 7/15/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private LinearLayout llSignupDetails;
    private Button btnSignup;
    private Button btnSignin;
    private EditText etSignupEmail, etSignupPassword, etSignupcPassword, etSigninEmail, etSigninPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);

        etSignupEmail = (EditText) findViewById(R.id.signup_email);
        etSignupPassword = (EditText) findViewById(R.id.signup_password);
        etSignupcPassword = (EditText) findViewById(R.id.signup_cPassword);
        etSigninEmail = (EditText) findViewById(R.id.signin_email);
        etSigninPassword = (EditText) findViewById(R.id.signin_password);


        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignupDetails = (LinearLayout) findViewById(R.id.llSignupDetails);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });


        showSigninForm();

        progressDialog = new ProgressDialog(LoginActivity.this);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }


    @Override
    public void onClick(View view) {
        String email, password, cPassword;

        switch (view.getId()) {

            case R.id.btnSignin:
               email = etSigninEmail.getText().toString();
               password = etSignupPassword.getText().toString();

                break;

            case R.id.btnSignup:

                //startActivity(new Intent(this, SignUpDetailsActivity.class));
                overridePendingTransition(R.anim.translate_right_to_left, R.anim.translate_left_to_right);

                email = etSignupEmail.getText().toString();
                password = etSignupPassword.getText().toString();
                cPassword = etSignupcPassword.getText().toString();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
                    etSignupEmail.setError("Please Enter an Email");
                    return;
                } else if(TextUtils.isEmpty(password)){
                    Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
                    etSignupPassword.setError("Please Enter the Password");
                    return;
                } else if(TextUtils.isEmpty(cPassword)){
                    Toast.makeText(this,"Please Confirm Your password",Toast.LENGTH_LONG).show();
                    etSignupPassword.setError("Please Confirm Your password");
                    return;
                }else if (password.equals(cPassword)) {

                            if(registerUser(email, password)){
                                startActivity(new Intent(LoginActivity.this, SignUpDetailsActivity.class));
                            } else {
                                Toast.makeText(getBaseContext(), "Unable to register, please try again after some time.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getBaseContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }

                break;

        }

    }

    private boolean registerUser(String email, String password){

        final boolean[] register = {false};

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            register[0] = true;
                            //display some message here
                            Toast.makeText(LoginActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            register[0] = false;
                            //display some message here
                            Toast.makeText(LoginActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
        return register[0];

    }

}
