package com.example.focofacil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Remove a linha abaixo se não desejar definir um tema específico
        // setTheme(R.style.AppTheme);
    }
}
