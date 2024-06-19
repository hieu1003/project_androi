package com.example.project_androi.screen;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appota.lunarcore.LunarCoreHelper;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.project_androi.R;
import phcom.edu.lichvannien.databaseEvent;
import com.example.project_androi.databinding.ActivityDetailEventBinding;
import com.example.project_androi.model.AlarmReceiver;
import com.example.project_androi.model.event;

public class DetailEventActivity extends AppCompatActivity {

    ActivityDetailEventBinding binding;
    private boolean isSave = false;
    private event mEvent;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    final String[] CAN = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    final String[] CHI = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mEvent = (event) getIntent().getSerializableExtra("event");

        binding.edtTime.setText(mEvent.getTime());
        binding.edtTitle.setText(mEvent.getTitle());
        binding.edtContent.setText(mEvent.getNote());
        binding.edtTime.setEnabled(false);
        binding.edtTitle.setEnabled(false);
        binding.edtContent.setEnabled(false);

        int mlunarDay[] = LunarCoreHelper.convertSolar2Lunar(mEvent.getDay(), mEvent.getMonth(), mEvent.getYear(), 7.0);
        int lunarDay = mlunarDay[0];
        int lunarMonth = mlunarDay[1];
        int lunarYear = mlunarDay[2];

        Calendar calendar = Calendar.getInstance();
        calendar.set(mEvent.getYear(), mEvent.getMonth()-1, mEvent.getDay());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        binding.tvMonth.setText("Thứ "+dayOfWeek+","+mEvent.getDay()+"/"+mEvent.getMonth()+"/"+mEvent.getYear());
        binding.tvDay.setText("Ngày\n\n"+lunarDay+"\n\n"+getCanChiDay(lunarDay,lunarMonth,lunarYear));
        binding.tvMonthLunar.setText("Tháng\n\n"+lunarMonth+"\n\n"+getCanChiMonth(lunarMonth,lunarYear));
        binding.tvYearLunar.setText("Năm\n\n"+lunarYear+"\n\n"+getCanChiYear(lunarYear));

        binding.edtTime.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Chọn giờ")
                    .build();
            materialTimePicker.show(getSupportFragmentManager(), "TIME_PICKER");
            materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                String hour = materialTimePicker.getHour() < 10 ? "0" + materialTimePicker.getHour() : materialTimePicker.getHour() + "";
                String minute = materialTimePicker.getMinute() < 10 ? "0" + materialTimePicker.getMinute() : materialTimePicker.getMinute() + "";
                binding.edtTime.setText(hour + ":" + minute);
            });
        });
        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(DetailEventActivity.this, CalenderMonthYear.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

        binding.btnAddEvent.setOnClickListener(v -> {
            if(isSave){
                String title = binding.edtTitle.getText().toString();
                String content = binding.edtContent.getText().toString();
                String hour = binding.edtTime.getText().toString();
                mEvent.setTitle(title);
                mEvent.setNote(content);
                mEvent.setTime(hour);
                new databaseEvent(DetailEventActivity.this).updateEv(mEvent, new databaseEvent.onUpdateSuccess() {
                    @Override
                    public void onSuccess() {
                        createNotification(title,content, mEvent.getYear(), mEvent.getMonth(), mEvent.getDay(), Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
                        startActivity(new Intent(DetailEventActivity.this, CalenderMonthYear.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                });
            }else{
                binding.btnAddEvent.setText("Lưu");
                binding.edtTime.setEnabled(true);
                binding.edtTitle.setEnabled(true);
                binding.edtContent.setEnabled(true);
                binding.edtTitle.requestFocus();
                isSave = true;
            }
        });

        binding.btnDeleteEvent.setOnClickListener(v -> {
            new SweetAlertDialog(DetailEventActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Bạn có chắc chắn muốn xóa sự kiện này?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        new databaseEvent(DetailEventActivity.this).deleteEv(mEvent, new databaseEvent.onDeleteSuccess() {
                            @Override
                            public void onSuccess() {
                                startActivity(new Intent(DetailEventActivity.this, CalenderMonthYear.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        });
                    })
                    .setCancelButton("Không", SweetAlertDialog::dismissWithAnimation)
                    .show();

        });
    }

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

        Intent intent = new Intent(this, AlarmReceiver.class);
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