package com.example.myloanapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {


    EditText name, username, password;
    Button signup;
    TextView login;
    FirebaseAuth firebaseAuth;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        loadint();


        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.b1);
        login = findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etname = name.getText().toString().trim();
                String etusername = username.getText().toString().trim();
                String etpass = password.getText().toString().trim();
                if (etname.isEmpty()) {
                    name.setError("Required");
                    return;
                }
                if (etusername.isEmpty()) {
                    username.setError("Required");
                    return;
                }
                if (etpass.isEmpty()) {
                    password.setError("Required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(etusername).matches()) {
                    username.setError("Invalid Email Format");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(etname,etpass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    Intent i= new Intent(SignupActivity.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(SignupActivity.this, "Thanks for being awesome!", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    Toast.makeText(SignupActivity.this, "Error:"+task.getException(), Toast.LENGTH_LONG).show();
                                    task.getException();
                                }
                            }
                        });


            }


        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SignupActivity.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdClicked() {
                            // Called when a click is recorded for an ad.

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Intent i =new Intent( SignupActivity.this,MainActivity.class);
                            startActivity(i);
                          loadint();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.

                            mInterstitialAd = null;
                        }

                        @Override
                        public void onAdImpression() {
                            // Called when an impression is recorded for an ad.

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.

                        }
                    });

                } else {

                }

            }
        });
    }
    public void loadint(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }
}