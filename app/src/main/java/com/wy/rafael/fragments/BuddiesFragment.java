package com.wy.rafael.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wy.rafael.R;
import com.wy.rafael.activities.RouteSlidePagerActivity;
import com.wy.rafael.adapters.BuddyAdapter;
import com.wy.rafael.models.Buddy;

public class BuddiesFragment extends Fragment implements RouteSlidePagerActivity.FragmentLifecycle {

    BuddyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_buddies, container, false);
        setUpRecyclerView(rootView);
        return rootView;
    }

    private void setUpRecyclerView(ViewGroup view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chooseBuddiesRecyclerView);
        adapter = new BuddyAdapter(getActivity(), Buddy.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        // default
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onPauseFragment() {

    }
}
