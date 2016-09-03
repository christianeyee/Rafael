package com.wy.rafael.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.wy.rafael.R;
import com.wy.rafael.fragments.BuddiesFragment;
import com.wy.rafael.fragments.DetailsFragment;
import com.wy.rafael.fragments.RouteFragment;

import java.util.ArrayList;
import java.util.List;

public class RouteSlidePagerActivity extends FragmentActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Button prev;
    private Button next;

    public interface FragmentLifecycle {
        public void onPauseFragment();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifecycle fragmentToHide = (FragmentLifecycle)((ScreenSlidePagerAdapter)mPagerAdapter).getItem(currentPosition);
            fragmentToHide.onPauseFragment();
            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) { }

        public void onPageScrollStateChanged(int arg0) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);

        setUpSlider();
        setUpButtons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpButtons() {
        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() > 0) {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() < mPagerAdapter.getCount() - 1) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
            }
        });
    }

    private void setUpSlider() {
        mPager = (ViewPager) findViewById(R.id.pager);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new DetailsFragment());
        fragments.add(new BuddiesFragment());
        fragments.add(new RouteFragment());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragments);
        mPager.addOnPageChangeListener(pageChangeListener);
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = new ArrayList<>(fragments);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
