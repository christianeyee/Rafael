package com.wy.rafael.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.wy.rafael.R;
import com.wy.rafael.fragments.tips.Tip1Fragment;
import com.wy.rafael.fragments.tips.Tip2Fragment;
import com.wy.rafael.fragments.tips.Tip3Fragment;
import com.wy.rafael.fragments.tips.Tip4Fragment;
import com.wy.rafael.fragments.tips.Tip5Fragment;

import java.util.ArrayList;
import java.util.List;

public class TipSlidePagerActivity extends FragmentActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_slide_pager);

        setUpSlider();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setUpSlider() {
        mPager = (ViewPager) findViewById(R.id.tipPager);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Tip1Fragment());
        fragments.add(new Tip2Fragment());
        fragments.add(new Tip3Fragment());
        fragments.add(new Tip4Fragment());
        fragments.add(new Tip5Fragment());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragments);
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
