package com.example.project_androi.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.example.project_androi.R;
import com.example.project_androi.adapter.adapterEvent;
import phcom.edu.lichvannien.databaseEvent;
import com.example.project_androi.databinding.ActivityListenventBinding;
import com.example.project_androi.model.event;

public class ListenventActivity extends AppCompatActivity implements adapterEvent.OnItemClickListener{
    SharedPreferences sharedPreferences;
    ActivityListenventBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListenventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        String value = sharedPreferences.getString("bg", "bg1");
        if (value.equals("bg1")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.bgradian1);
        }
        if (value.equals("bg2")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.bgradian2);
        }
        if (value.equals("bg3")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.bgradian3);
        }
        if (value.equals("bg4")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.bgradian4);
        }
        if (value.equals("bg5")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.nen);
        }
        if (value.equals("bg6")) {
            binding.constraintLayout.setBackgroundResource(R.drawable.bg);
        }


    }

    @Override
    public void onItemClick(event event) {
        Intent intent = new Intent(ListenventActivity.this, DetailEventActivity.class);
        intent.putExtra("event",event);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new databaseEvent(ListenventActivity.this).getAllEv(new databaseEvent.onGetAllSuccess() {
            @Override
            public void onSuccess(List<event> list) {
                binding.rvEvent.setAdapter(new adapterEvent(ListenventActivity.this,list));
                binding.rvEvent.setHasFixedSize(true);
                binding.rvEvent.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(ListenventActivity.this));
            }
        });
    }
}