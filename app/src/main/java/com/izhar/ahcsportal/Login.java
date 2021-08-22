package com.izhar.ahcsportal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        if (getSharedPreferences("user", MODE_PRIVATE).getString("status", "logged out").equalsIgnoreCase("logged in")){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        login.setOnClickListener(v -> {
            if (valid()){
                get_login();
            }

        });
    }

    private void get_login() {
        String url = API.host_ip + "check/" + username.getText().toString() + "/" + password.getText().toString() + "/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonArray = new JSONObject(response);
                    Toast.makeText(Login.this, jsonArray.getString("username"), Toast.LENGTH_SHORT).show();
                    getSharedPreferences("user", MODE_PRIVATE).edit().putString("status", "logged in").putString("username", username.getText().toString()).apply();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } catch (Exception e) {
                    Toast.makeText(Login.this, "error occurred", Toast.LENGTH_LONG).show();
                }
            }
        }, error -> {
            Toast.makeText(this, "invalid username or password", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(stringRequest);
    }

    private boolean valid() {
        if (username.getText().toString().length() == 0){
            username.setError("username required");
            return false;
        }
        if (password.getText().toString().length() == 0){
            password.setError("password required");
            return false;
        }
        return true;
    }
}