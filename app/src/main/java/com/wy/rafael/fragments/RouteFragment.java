package com.wy.rafael.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.wy.rafael.R;
import com.wy.rafael.activities.RouteSlidePagerActivity;
import com.wy.rafael.services.SmsService;

import java.util.Timer;
import java.util.TimerTask;

public class RouteFragment extends Fragment implements RouteSlidePagerActivity.FragmentLifecycle {

    // in milliseconds
    private final int DEFAULT_INTERVAL = 5000;
    private final int DEFAULT_DURATION = 30000;

    private String destination;
    private int interval;
    private int duration;
    private int count;

    private CountDownTimer timer;

    private ViewGroup rootView;
    private WebView wv;
    private Button reset;
    private TextView tvHour, tvMinute, tvSecond;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_route, container, false);
        getItinerary();
        setUpWebView(rootView);
        setUpTimer(rootView);
        setUpButton(rootView);
        return rootView;
    }

    private void getItinerary() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        duration = sharedPref.getInt(getString(R.string.sp_duration_id), DEFAULT_DURATION);
        interval = sharedPref.getInt(getString(R.string.sp_interval_id), DEFAULT_INTERVAL);
        destination = sharedPref.getString(getString(R.string.sp_destination_id), getString(R.string.location_default));
        count = duration / interval;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            timer.start();
            setUpTask();
        }
        else {  }
    }

    private void setUpButton(ViewGroup vg) {
        reset = (Button) vg.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.setText("Reset timer");
                timer.cancel();
                timer.start();
            }
        });
    }

    private void setUpTask() {
        MyTimerTask myTask = new MyTimerTask();
        Timer myTimer = new Timer();
        myTimer.schedule(myTask, interval, interval);
    }

    @Override
    public void onPauseFragment() {

    }

    class MyTimerTask extends TimerTask {
        public void run() {
            if (count > 0) {
                reportLocation();
                count--;
            }
        }
    }

    private void reportLocation() {
        Intent intent = new Intent(getActivity(), SmsService.class);
        intent.putExtra(getString(R.string.location_extra), destination);
        getActivity().startService(intent);
    }

    private void setUpTimer(ViewGroup vg) {
        tvHour = (TextView) vg.findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) vg.findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) vg.findViewById(R.id.txtTimerSecond);

        timer = new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                long hours = millisUntilFinished / 3600000;
                long minutes = (millisUntilFinished % 3600000) / 60000;
                long seconds = ((millisUntilFinished % 3600000) % 60000) / 1000;

                tvHour.setText("" + String.format("%02d", hours));
                tvMinute.setText("" + String.format("%02d", minutes));
                tvSecond.setText("" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tvHour.setText("" + String.format("%02d", 0));
                tvMinute.setText("" + String.format("%02d", 0));
                tvSecond.setText("" + String.format("%02d", 0));

                reset.setText("Sending current location to buddies...");
                reportLocation();
            }
        };
    }

    private void setUpWebView(ViewGroup vg) {
        wv = (WebView) vg.findViewById(R.id.webView);
        wv.setWebViewClient(new MyBrowser());

        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String url = "\"" + getString(R.string.maps_url)
                    + "key=" + getString(R.string.maps_key)
                    + "origin=" + getString(R.string.location_default) + "&"
                    + "destination=" + destination + "\"";

        wv.loadData("<iframe src=" + url + "></iframe>", "text/html", "utf-8");
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
