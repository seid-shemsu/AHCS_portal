package com.izhar.ahcsportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IP extends AppCompatActivity {

    EditText ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_p);
        ip = findViewById(R.id.ip);
    }

    public void ok(View view) {
        if (ip.getText().toString().length() > 0){
            getSharedPreferences("user", MODE_PRIVATE).edit().putString("ip", ip.getText().toString()).apply();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}