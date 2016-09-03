package com.wy.rafael.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.wy.rafael.R;
import com.wy.rafael.activities.RouteSlidePagerActivity;

public class DetailsFragment extends Fragment implements RouteSlidePagerActivity.FragmentLifecycle {

    AutoCompleteTextView destinationView;
    EditText durationView;
    EditText intervalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_details, container, false);

        destinationView = (AutoCompleteTextView) rootView.findViewById(R.id.destination);
        durationView = (EditText) rootView.findViewById(R.id.eta);
        intervalView = (EditText) rootView.findViewById(R.id.interval);

        return rootView;
    }

    @Override
    public void onPauseFragment() {
        String destination = destinationView.getText().toString();
        int duration = 1000 * Integer.parseInt(durationView.getText().toString());
        int interval = 1000 * Integer.parseInt(intervalView.getText().toString());

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.sp_destination_id), destination);
        editor.putInt(getString(R.string.sp_duration_id), duration);
        editor.putInt(getString(R.string.sp_interval_id), interval);
        editor.commit();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
