package com.wy.rafael.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wy.rafael.R;
import com.wy.rafael.adapters.BuddyAdapter;
import com.wy.rafael.models.Buddy;

public class BuddiesActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BuddyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddies);

        fab = (FloatingActionButton) findViewById(R.id.addBuddyFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuddy();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.buddiesRecyclerView);
        adapter = new BuddyAdapter(this, Buddy.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        // default
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void addBuddy() {
        Intent intent = new Intent(this, CreateBuddyActivity.class);
        startActivity(intent);
    }
}
