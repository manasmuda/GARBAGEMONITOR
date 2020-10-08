package com.example.garbagemonitor;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.garbagemonitor.CurrentFragment;
import com.example.garbagemonitor.HistoryFragment;

public class FragmentPager extends FragmentStatePagerAdapter {

    private Context mContext;

    public FragmentPager(FragmentManager fm, Context context) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext=context;

    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return CurrentFragment.getInstance(mContext);
            case 1:
                return HistoryFragment.getInstance(mContext);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void sendId(String id){
        CurrentFragment.getInstance(mContext).setId(id);
        CurrentFragment.getInstance(mContext).setId(id);
    }
}
