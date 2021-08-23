package com.izhar.ahcsportal;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.izhar.ahcsportal.notification.AppointmentNotification;
import com.izhar.ahcsportal.notification.MedicationNotification;
import com.izhar.ahcsportal.objects.MedicationObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Appointment extends Fragment {

    CalendarView calendar_view;
    View root;
    TextView hospital, doctor, case_detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_appointment, container, false);
        createChannel();
        calendar_view = root.findViewById(R.id.calendar);
        case_detail = root.findViewById(R.id.case_detail);
        String url = API.host_ip + "get_appointment/" + getContext().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "null");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    String[] parts = date.split("-");

                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.clear();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month-1);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.HOUR_OF_DAY, 6);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.AM_PM, Calendar.AM);

                    if (requireContext().getSharedPreferences("appointment", Context.MODE_PRIVATE).getString(case_, "not set").equalsIgnoreCase("not set")) {
                        Intent intent = new Intent(getContext(), MedicationNotification.class)
                                .putExtra("title", "Appointment")
                                .putExtra("desc", "Check your appointment");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 101, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
                        requireContext().getSharedPreferences("appointment", Context.MODE_PRIVATE).edit().putString(case_, "set").apply();
                    }

                    calendar_view.setDate(calendar.getTimeInMillis(),true,true);

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(getContext(), error.getMessage() + " ", Toast.LENGTH_SHORT).show());
        requestQueue.add(stringRequest);
        return root;
    }
    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "AppointmentNotificationChannel";
            String desc = "channel for medication notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Appointment_notification", name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
