package com.calcatz.buahloka;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.calcatz.buahloka.BerandaFragment;
import com.calcatz.buahloka.FavoritFragment;
import com.calcatz.buahloka.KeranjangFragment;

public class CustomFragmentPageAdapter extends FragmentPagerAdapter{
    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 3;
    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BerandaFragment();
            case 1:
                return new FavoritFragment();
            case 2:
                return new KeranjangFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Beranda";
            case 1:
                return "Favorit";
            case 2:
                return "Keranjang";
        }
        return null;
    }
}