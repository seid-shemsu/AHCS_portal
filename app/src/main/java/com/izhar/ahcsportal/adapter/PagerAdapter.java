package com.izhar.ahcsportal.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.izhar.ahcsportal.Appointment;
import com.izhar.ahcsportal.Medication;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tab_numbers;
    public PagerAdapter(@NonNull FragmentManager fm, int tab_numbers) {
        super(fm);
        this.tab_numbers = tab_numbers;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                Medication medication = new Medication();
                return medication;
            case 0:
                Appointment appointment = new Appointment();
                return appointment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tab_numbers;
    }
}