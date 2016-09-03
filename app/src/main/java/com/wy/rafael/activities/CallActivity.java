package com.wy.rafael.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wy.rafael.R;
import com.wy.rafael.adapters.RecordingAdapter;
import com.wy.rafael.models.Recording;

public class CallActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecordingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        fab = (FloatingActionButton) findViewById(R.id.addRecordingFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recordingsRecyclerView);
        adapter = new RecordingAdapter(CallActivity.this, Recording.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        // default
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
