package com.example.project_androi.screen;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_androi.databinding.ActivityImageBgBinding;

public class ImageBgActivity extends AppCompatActivity {
    ActivityImageBgBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageBgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        binding.cvAnhnen1.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg1").apply();
            finish();
        });
        binding.cvAnhnen2.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg2").apply();
            finish();
        });
        binding.cvAnhnen3.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg3").apply();
            finish();
        });
        binding.cvAnhnen4.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg4").apply();
            finish();
        });
        binding.cvAnhnen5.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg5").apply();
            finish();
        });
        binding.cvAnhnen6.setOnClickListener(v -> {
            sharedPreferences.edit().putString("bg", "bg6").apply();
            finish();
        });
    }
}