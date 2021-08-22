package com.izhar.ahcsportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.izhar.ahcsportal.adapter.MedicationAdapter;
import com.izhar.ahcsportal.objects.MedicationObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


public class Appointment extends Fragment {

    CalendarView calendar_view;
    View root;
    TextView hospital, doctor, case_detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_appointment, container, false);
        calendar_view = root.findViewById(R.id.calendar);
        case_detail = root.findViewById(R.id.case_detail);
        String url = API.host_ip + "get_appointment/" + getContext().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "null");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    //String id, booked_date, appointment_date, case_, status, physician, patient, hostpital;
                    String appointment_date = jsonObject.getString("appointment_date"), case_ = jsonObject.getString("case");
                    case_detail.setText(case_);
                    String date = appointment_date.substring(0, appointment_date.indexOf('T'));
                    String parts[] = date.split("-");

                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, (month - 1));
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    long milliTime = calendar.getTimeInMillis();

                    calendar_view.setDate(milliTime,true,true);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage() + " ", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
        return root;
    }
}

    /*String date = "25/2/2016";
    String parts[] = date.split("/");

    int day = Integer.parseInt(parts[0]);
    int month = Integer.parseInt(parts[1]);
    int year = Integer.parseInt(parts[2]);

    Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month - 1));
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        CalendarView calendarView = (CalendarView)findViewById(R.id.cview);
        calendarView.setDate(milliTime,true,true);*/