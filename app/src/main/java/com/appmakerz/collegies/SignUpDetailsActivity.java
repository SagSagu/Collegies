package com.appmakerz.collegies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;

public class SignUpDetailsActivity extends AppCompatActivity {
    private EditText et_name, et_usn, et_sem, et_mobile;
    private LoadingButton lb;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_up_details_layout);

        try {
            lb = (LoadingButton) findViewById(R.id.loading_btn);
            et_name = (EditText) findViewById(R.id.signUpDeName);
            et_usn = (EditText) findViewById(R.id.signUpDeName);
            et_sem = (EditText) findViewById(R.id.signUpDeName);
            et_mobile = (EditText) findViewById(R.id.signUpDeName);


            lb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lb.startLoading();
                    lb.loadingSuccessful();
                }
            });


            lb.postDelayed(new Runnable() {
                @Override
                public void run() {

                 //   if ("".endsWith(et_name.getText().toString())) {

                        lb.loadingSuccessful();

                        startActivity(new Intent(SignUpDetailsActivity.this,SecondActivity.class));
                        Toast.makeText(getApplicationContext(), "login ,please check username and password", Toast.LENGTH_SHORT).show();

                       /* lb.setAnimationEndListener(new LoadingButton.AnimationEndListener() {

                            @Override
                            public void onAnimationEnd(LoadingButton.AnimationType animationType) {

                            }
                        });*/
              /*      } else {
                        lb.loadingFailed();
                        Toast.makeText(getApplicationContext(), "login failed,please check username and password", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }, 3000);


        } catch (Exception ex) {

        }
    }


}


