package com.izhar.ahcsportal;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.izhar.ahcsportal.adapter.MedicationAdapter;
import com.izhar.ahcsportal.notification.MedicationNotification;
import com.izhar.ahcsportal.objects.MedicationObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Medication extends Fragment {

    View root;
    RecyclerView recycler;
    TextView no_medication;
    ProgressBar progress;
    List<MedicationObject> medications;
    MedicationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_medication, container, false);
        createChannel();
        recycler = root.findViewById(R.id.recycler);
        no_medication = root.findViewById(R.id.no_medication);
        progress = root.findViewById(R.id.progress);
        medications = new ArrayList<>();
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        String url = API.host_ip + "get_medications/" + getContext().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "null");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id, medication, dosage, description, start_date, end_date;
                        id = jsonObject.getString("id");
                        start_date = jsonObject.getString("start_date");
                        end_date = jsonObject.getString("end_date");
                        medication = jsonObject.getString("medication_name");
                        dosage = jsonObject.getString("frequency_perday");
                        description = jsonObject.getString("description");
                        medications.add(new MedicationObject(id, medication, dosage, description, start_date, end_date));
                    }
                    adapter = new MedicationAdapter(getContext(), medications);
                    recycler.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                    if (medications.size() == 0) {
                        no_medication.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage() + " ", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                no_medication.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(stringRequest);
        return root;
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "medicationNotificationChannel";
            String desc = "channel for medication notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("medication_notification", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}