package com.wy.rafael;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wy.rafael.activities.BuddiesActivity;
import com.wy.rafael.activities.CallActivity;
import com.wy.rafael.activities.LoginActivity;
import com.wy.rafael.activities.RouteSlidePagerActivity;
import com.wy.rafael.activities.TipSlidePagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private String userName;

    private Button routeButton;
    private Button callButton;
    private Button buddiesButton;
    private Button tipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    userName = user.getDisplayName();
                }
            }
        };

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        routeButton = (Button) findViewById(R.id.route_button);
        callButton = (Button) findViewById(R.id.call_button);
        buddiesButton = (Button) findViewById(R.id.buddies_button);
        tipButton = (Button) findViewById(R.id.tip_button);

        routeButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
        buddiesButton.setOnClickListener(this);
        tipButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.route_button:
                startRoute();
                break;
            case R.id.call_button:
                startCall();
                break;
            case R.id.buddies_button:
                startBuddies();
                break;
            case R.id.tip_button:
                startTip();
                break;
        }
    }

    private void startRoute() {
        Intent intent = new Intent(this, RouteSlidePagerActivity.class);
        startActivity(intent);
    }
    private void startCall() {
        Intent intent = new Intent(this, CallActivity.class);
        startActivity(intent);
    }
    private void startBuddies() {
        Intent intent = new Intent(this, BuddiesActivity.class);
        startActivity(intent);
    }
    private void startTip() {
        Intent intent = new Intent(this, TipSlidePagerActivity.class);
        startActivity(intent);
    }
}
