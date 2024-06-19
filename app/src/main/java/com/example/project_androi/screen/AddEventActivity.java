package com.example.project_androi.screen;

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
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appota.lunarcore.LunarCoreHelper;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

import com.example.project_androi.R;
import phcom.edu.lichvannien.databaseEvent;
import com.example.project_androi.databinding.ActivityAddEventBinding;
import com.example.project_androi.model.AlarmReceiver;
import com.example.project_androi.model.ItemDay;
import com.example.project_androi.model.event;

public class AddEventActivity extends AppCompatActivity {

    ActivityAddEventBinding binding;
    private ItemDay itemDay;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    final String[] CAN = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    final String[] CHI = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemDay = (ItemDay) getIntent().getSerializableExtra("itemDay");
        createNotificationChannel();

        int mlunarDay[] = LunarCoreHelper.convertSolar2Lunar(itemDay.getDay(), itemDay.getMonth(), itemDay.getYear(), 7.0);
        int lunarDay = mlunarDay[0];
        int lunarMonth = mlunarDay[1];
        int lunarYear = mlunarDay[2];


        binding.tvMonth.setText("Thứ "+itemDay.getWeekday()+","+itemDay.getDay()+"/"+itemDay.getMonth()+"/"+itemDay.getYear());
        binding.tvDay.setText("Ngày\n\n"+lunarDay+"\n\n"+getCanChiDay(lunarDay,lunarMonth,lunarYear));
        binding.tvMonthLunar.setText("Tháng\n\n"+lunarMonth+"\n\n"+getCanChiMonth(lunarMonth,lunarYear));
        binding.tvYearLunar.setText("Năm\n\n"+lunarYear+"\n\n"+getCanChiYear(lunarYear));
        binding.edtTime.setOnClickListener(new View.OnClickListener() {//chọn giờ bằng time picker của material
            @Override
            public void onClick(View v) {
                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()//chọn giờ
                        .setTimeFormat(TimeFormat.CLOCK_24H)//định dạng 24h cho time picker
                        .setTitleText("Chọn giờ")
                        .build();
                materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");//hiển thị time picker
                materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                    String hour = materialTimePicker.getHour() < 10 ? "0" + materialTimePicker.getHour() : materialTimePicker.getHour() + "";//nếu giờ nhỏ hơn 10 thì thêm số 0 vào trước giờ
                    String minute = materialTimePicker.getMinute() < 10 ? "0" + materialTimePicker.getMinute() : materialTimePicker.getMinute() + "";//nếu phút nhỏ hơn 10 thì thêm số 0 vào trước phút
                    binding.edtTime.setText(hour + ":" + minute);
                });
            }
        });

        binding.btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.edtTitle.getText().toString();
                String content = binding.edtContent.getText().toString();
                String hour = binding.edtTime.getText().toString();

                if (title.isEmpty() || content.isEmpty() || hour.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    event mevent = new event(title, itemDay.getDay(),itemDay.getMonth(),itemDay.getYear(), hour, content);
                    new databaseEvent(getApplicationContext()).insertEv(mevent, new databaseEvent.onInsertSuccess() {
                        @Override
                        public void onSuccess() {
                            //int year, int month, int day, int hour, int minute
                            createNotification(title,content, itemDay.getYear(), itemDay.getMonth(), itemDay.getDay(), Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
                            startActivity(new Intent(AddEventActivity.this, CalenderMonthYear.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    });
                }
            }
        });

        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(AddEventActivity.this, CalenderMonthYear.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

        });
    }

    //tính can chi của ngày tháng năm
    public String getCanChiYear(int year) {
        int can = year % 10;
        int chi = year % 12;
        String canChi = CAN[can] + " " + CHI[chi];
        return canChi;
    }
    public String getCanChiMonth(int month, int year) {
        int can = (year * 12 + month + 3) % 10;
        int chi = (month + 1) % 12;
        String canChi = CAN[can] + " " + CHI[chi];
        return canChi;
    }
    //tính can chi của ngày tháng năm
    public String getCanChiDay(int day, int month, int year) {
        int can = (year * 12 + month + 3) % 10;
        int chi = (month + 1) % 12;
        int canDay = (can * 2 + chi + day) % 10;
        int chiDay = day % 12;
        String canChi = CAN[canDay] + " " + CHI[chiDay];
        return canChi;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        String value = sharedPreferences.getString("bg", "bg1");
        if (value.equals("bg1")) {
            binding.scrollView.setBackgroundResource(R.drawable.bgradian1);
        }
        if (value.equals("bg2")) {
            binding.scrollView.setBackgroundResource(R.drawable.bgradian2);
        }
        if (value.equals("bg3")) {
            binding.scrollView.setBackgroundResource(R.drawable.bgradian3);
        }
        if (value.equals("bg4")) {
            binding.scrollView.setBackgroundResource(R.drawable.bgradian4);
        }
        if (value.equals("bg5")) {
            binding.scrollView.setBackgroundResource(R.drawable.nen);
        }
        if (value.equals("bg6")) {
            binding.scrollView.setBackgroundResource(R.drawable.bg);
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void createNotification(String title,String content, int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "akchannel";
            String desc = "Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, imp);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}