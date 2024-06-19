package com.example.project_androi.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import phcom.edu.lichvannien.R;
import phcom.edu.lichvannien.databinding.ActivityMoreBinding;

public class MoreActivity extends AppCompatActivity {
    ActivityMoreBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.cvAnhnen.setOnClickListener(v -> {
            startActivity(new Intent(MoreActivity.this, ImageBgActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.cvEvent.setOnClickListener(v -> {
            startActivity(new Intent(MoreActivity.this, ListenventActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.tvThang.setOnClickListener(v -> {
            startActivity(new Intent(MoreActivity.this, CalenderMonthYear.class));

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        binding.tvNgay.setOnClickListener(v -> {
            startActivity(new Intent(MoreActivity.this, MainActivity.class));

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        String value = sharedPreferences.getString("bg", "bg1");
        if (value.equals("bg1")) {
            binding.rlMore.setBackgroundResource(R.drawable.bgradian1);
        }
        if (value.equals("bg2")) {
            binding.rlMore.setBackgroundResource(R.drawable.bgradian2);
        }
        if (value.equals("bg3")) {
            binding.rlMore.setBackgroundResource(R.drawable.bgradian3);
        }
        if (value.equals("bg4")) {
            binding.rlMore.setBackgroundResource(R.drawable.bgradian4);
        }
        if (value.equals("bg5")) {
            binding.rlMore.setBackgroundResource(R.drawable.nen);
        }
        if (value.equals("bg6")) {
            binding.rlMore.setBackgroundResource(R.drawable.bg);
        }

    }
}