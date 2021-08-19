package com.izhar.ahcsportal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;


public class Appointment extends Fragment {

    CalendarView calendar;
    View root;
    TextView hospital, doctor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_appointment, container, false);
        calendar = root.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(null);
        return root;
    }
}