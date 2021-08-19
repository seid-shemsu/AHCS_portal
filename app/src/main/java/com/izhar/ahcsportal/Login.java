package com.izhar.ahcsportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        login.setOnClickListener(v -> {
            if (valid()){
                getSharedPreferences("patientInfo", MODE_PRIVATE).edit().putString("patient_id", username.getText().toString().substring(0,1)).apply();
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }

        });
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