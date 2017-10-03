package com.example.asanaapp;

/**
 * Created by Usser on 13.11.2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Usser on 13.11.2016.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Asanas", "Sequences", "Statistics","Settings" };

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //return an instance of a fragment to be displayed, depending on chosen tab
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show AsanasFragment
                return AsanasFragment.newInstance();
            case 1: // Fragment # 0 - This will show SequenceFragment
                return SequenceFragment.newInstance();
            case 2: // Fragment # 1 - This will show StatisticsFragment
                return StatisticsFragment.newInstance();
            case 3: // Fragment # 1 - This will show SettingsFragment
                return SettingsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}