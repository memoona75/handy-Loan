package com.example.myloanapplication;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;


import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText et1, et2;
    Button bt1;
    TextView t1, t2;
    CheckBox checkBox;
    private FirebaseAuth mAuth;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);


        t2 = findViewById(R.id.terms);
        t1 = findViewById(R.id.signup);
        et1 = findViewById(R.id.username);
        et2 = findViewById(R.id.password);
        bt1 = findViewById(R.id.login_btn);
        checkBox = findViewById(R.id.checkbox);

        mAuth = FirebaseAuth.getInstance();

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);

            }
        });


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, TermsActivity.class);
                startActivity(i);

            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et1.getText().toString().trim();
                String password = et2.getText().toString().trim();
                if (username.isEmpty()) {
                    et1.setError("Required");
                    return;
                }
                if (password.isEmpty()) {
                    et2.setError("Required");
                    return;


                }
                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    et1.setError("Invalid Email Format ");
                    return;
                }
                if (!checkBox.isChecked()) {
                    t2.setError("Accept");
                    return;
                }

                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent iv = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(iv);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}