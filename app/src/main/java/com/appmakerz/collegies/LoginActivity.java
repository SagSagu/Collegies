package com.appmakerz.collegies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private  ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);

        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
                    /*DoubleBounce doubleBounce = new DoubleBounce();
                    progressBar.setIndeterminateDrawable(doubleBounce);*/
        WanderingCubes wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);

      progressBar.setVisibility(View.INVISIBLE);



        etSignupEmail = (EditText) findViewById(R.id.signup_email);
        etSignupPassword = (EditText) findViewById(R.id.signup_password);
        etSignupcPassword = (EditText) findViewById(R.id.signup_cPassword);
        etSigninEmail = (EditText) findViewById(R.id.signin_email);
        etSigninPassword = (EditText) findViewById(R.id.signin_password);


        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);

       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Sign Up", Toast.LENGTH_SHORT).show();
            }
        });*/

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
       // llSignupDetails = (LinearLayout) findViewById(R.id.llSignupDetails);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();


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

      /*  btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                if(isSigninScreen)
                    btnSignup.startAnimation(clockwise);

                Toast.makeText(getBaseContext(),"hdfjfhj",Toast.LENGTH_SHORT).show();

            }
        });*/
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
        progressBar.setVisibility(View.INVISIBLE);

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

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View view) {
        String email, password, cPassword;

        switch (view.getId()) {

            case R.id.btnSignin:
               email = etSigninEmail.getText().toString();
               password = etSigninPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Successfull",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, SignUpDetailsActivity.class));
                            overridePendingTransition(R.anim.translate_right_to_left, R.anim.translate_left_to_right);


                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login Failed",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });

                break;

            case R.id.btnSignup:



                email = etSignupEmail.getText().toString();
                password = etSignupPassword.getText().toString();
                cPassword = etSignupcPassword.getText().toString();


                boolean validate = validates();

                //if(validate) {

                    progressBar.setVisibility(View.VISIBLE);


                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "Authentication done",
                                                Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(LoginActivity.this, SignUpDetailsActivity.class));
                                        overridePendingTransition(R.anim.translate_right_to_left, R.anim.translate_left_to_right);


                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                //}



                /*if (password.equals(cPassword)) {
                    Toast.makeText(getBaseContext(), "Sign Up", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }*/


                break;


        }


    }

    public boolean validates(){

        if (TextUtils.isEmpty(etSignupEmail.getText().toString())) {
            etSignupEmail.setError("Please Enter Your Valid e-mail id");
            final Animation animShake= AnimationUtils.loadAnimation(this,R.anim.anim_shake);
            etSignupEmail.startAnimation(animShake);
            return false;
        } else {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            Pattern patternEmail = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
            Matcher matcherEmail = patternEmail.matcher(etSignupEmail.getText().toString());
            if (!matcherEmail.find()) {
                etSignupEmail.setError("Please Enter Your Valid e-mail id");
                final Animation animShake= AnimationUtils.loadAnimation(this,R.anim.anim_shake);
                etSignupEmail.startAnimation(animShake);
                return false;
            }
        }

        if (TextUtils.isEmpty(etSignupPassword.getText().toString())) {
            etSignupPassword.setError("Please Enter Your New Password");
            final Animation animShake= AnimationUtils.loadAnimation(this,R.anim.anim_shake);
            etSignupPassword.startAnimation(animShake);
            return false;
        } else if (etSignupPassword.length() < 6) {
            etSignupPassword.setError("Please Enter 6 OR More Characters");
            final Animation animShake= AnimationUtils.loadAnimation(this,R.anim.anim_shake);
            etSignupPassword.startAnimation(animShake);
                return false;
        }

        if (etSignupcPassword.getText().length() != etSignupPassword.getText().length() || etSignupcPassword.getText().equals(etSignupPassword.getText().toString())) {
            etSignupcPassword.setError("Mismatch Password");
            final Animation animShake= AnimationUtils.loadAnimation(this,R.anim.anim_shake);
            etSignupcPassword.startAnimation(animShake);
            return false;
        }

        return true;
    }
}
