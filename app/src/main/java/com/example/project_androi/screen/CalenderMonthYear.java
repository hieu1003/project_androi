package com.example.project_androi.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.appota.lunarcore.LunarCoreHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.project_androi.R;
import com.example.project_androi.adapter.CalendarAdapter;
import com.example.project_androi.adapter.WeekdayAdapter;
import phcom.edu.lichvannien.databaseEvent;
import com.example.project_androi.databinding.ActivityCalenderMonthYearBinding;
import com.example.project_androi.model.ItemDay;
import com.example.project_androi.model.event;

public class CalenderMonthYear extends AppCompatActivity implements CalendarAdapter.OnItemDayClickListener {

    ActivityCalenderMonthYearBinding binding;
    private List<ItemDay> listDay = new ArrayList<>();
    private List<event> listEvent = new ArrayList<>();
    private int lunarDay, lunarMonth, lunarYear, day, month, year, daysInMonth, dayOfWeek;
    private int[] solarDay, mlunarDay;
    private boolean isEvent = false;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private SharedPreferences sharedPreferences;
    private List<ItemDay> listDayEvent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalenderMonthYearBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GridView weekdayGridView = findViewById(R.id.weekday_gridview);
        WeekdayAdapter weekdayAdapter = new WeekdayAdapter(this);
        weekdayGridView.setAdapter(weekdayAdapter);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //spps ngay trong thang
        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        binding.next.setOnClickListener(v -> {
            if (month == 12) {
                month = 1;
            } else {
                month++;
            }
            binding.tvMonth.setText("Tháng " + month + "-" + year);
            updateLich();
        });

        binding.previous.setOnClickListener(v -> {
            if (month == 1) {
                month = 12;
            } else {
                month--;
            }
            updateLich();
        });

        binding.tvNgay.setOnClickListener(v -> {
            Intent intent = new Intent(CalenderMonthYear.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.tvMr.setOnClickListener(v -> {
            Intent intent = new Intent(CalenderMonthYear.this, MoreActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    private void insertItemDay() {
        int year = calendar.get(Calendar.YEAR);
        for (int i = 1; i <= 12; i++) {
            calendar.set(Calendar.MONTH, i-1);
            calendar.set(Calendar.YEAR, 2023);
            for (int j = 1; j <= 31; j++) {
                calendar.set(Calendar.DAY_OF_MONTH, j);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//ngay trong tuan
                mlunarDay = LunarCoreHelper.convertSolar2Lunar(j, i, year, 7);
                ItemDay itemDay = new ItemDay(j, i, year, mlunarDay[0], mlunarDay[1], mlunarDay[2], dayOfWeek, false);
                new databaseEvent(this).insertItemDay(itemDay, new databaseEvent.onInsertSuccess() {
                    @Override
                    public void onSuccess() {

                    }
                });
            }
        }
        sharedPreferences = getSharedPreferences("insert", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("insert", true);
        editor.apply();
    }

    private void updateLich() {
        listDay.clear();
        new databaseEvent(this).getAllEv(new databaseEvent.onGetAllSuccess() {
            @Override
            public void onSuccess(List<event> list) {
                listEvent.clear();
                listEvent = list;
                new databaseEvent(CalenderMonthYear.this).getItemDay(month,new databaseEvent.onGetItemAllSuccess() {
                    @Override
                    public void onSuccess(List<ItemDay> list) {
                        listDay.clear();
                        listDay = list;
                    }
                });
                if (listEvent.size() > 0) {
                    for (int i = 0; i < listEvent.size(); i++) {
                        for (int j = 0; j < listDay.size(); j++) {
                            if (listEvent.get(i).getDay() == listDay.get(j).getDay() && listEvent.get(i).getMonth() == listDay.get(j).getMonth() && listEvent.get(i).getYear() == listDay.get(j).getYear()) {
                                listDay.get(j).setEvent(true);
                            }
                        }
                    }
                }
                //sap xep ngay trong thang
                if (listDay.get(0).

                        getWeekday() == 2) { //neu ngay dau tien la thu 2 thi them 1 ngay thu 7 vao truoc ngay dau tien
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                if (listDay.get(0).

                        getWeekday() == 3) {//neu ngay dau tien la thu 3 thi them 2 ngay thu 7 vao truoc ngay dau tien
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(1, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                if (listDay.get(0).

                        getWeekday() == 4) {
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(1, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(2, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                if (listDay.get(0).

                        getWeekday() == 5) {
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(1, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(2, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(3, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                if (listDay.get(0).

                        getWeekday() == 6) {
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(1, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(2, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(3, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(4, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                if (listDay.get(0).

                        getWeekday() == 7) {
                    listDay.add(0, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(1, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(2, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(3, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(4, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                    listDay.add(5, new ItemDay(0, 0, 0, 0, 0, 0, 0));
                }
                binding.tvMonth.setText("Tháng " + month + "-" + year);
                GridView gridView = findViewById(R.id.calendar_gridview);
                CalendarAdapter adapter = new CalendarAdapter(CalenderMonthYear.this, listDay);
                gridView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onItemDayClick(ItemDay itemDay) {
        if (itemDay.isEvent()) {
            for (event event : listEvent) {
                if (event.getDay() == itemDay.getDay() && event.getMonth() == itemDay.getMonth() && event.getYear() == itemDay.getYear()) {
                    //bạn có 1 sự kiện vào ngày này bạn có muốn xem sự kiện này không
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Bạn có muốn xem sự kiện này không?")
                            .setConfirmText("Có")
                            .setCancelText("Không")
                            .setConfirmClickListener(sDialog -> {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(this, DetailEventActivity.class);
                                intent.putExtra("event", event);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            })
                            .setCancelClickListener(sDialog -> sDialog.dismissWithAnimation())
                            .show();
                }
            }

        } else {
            //bạn có muốn thêm sự kiện vào ngày này không
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Bạn có muốn thêm sự kiện vào ngày này không?")
                    .setConfirmText("Có")
                    .setCancelText("Không")
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(this, AddEventActivity.class);
                        intent.putExtra("itemDay", itemDay);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    })
                    .setCancelClickListener(sDialog -> sDialog.dismissWithAnimation())
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("insert", MODE_PRIVATE);
        boolean insert = sharedPreferences.getBoolean("insert", false);
        if (!insert) {
            insertItemDay();
            Log.d("insert", "insert");
        }
        updateLich();
        sharedPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        String value = sharedPreferences.getString("bg", "bg1");
        if (value.equals("bg1")) {
            binding.rlMain.setBackgroundResource(R.drawable.bgradian1);
        }
        if (value.equals("bg2")) {
            binding.rlMain.setBackgroundResource(R.drawable.bgradian2);
        }
        if (value.equals("bg3")) {
            binding.rlMain.setBackgroundResource(R.drawable.bgradian3);
        }
        if (value.equals("bg4")) {
            binding.rlMain.setBackgroundResource(R.drawable.bgradian4);
        }
        if (value.equals("bg5")) {
            binding.rlMain.setBackgroundResource(R.drawable.nen);
        }
        if (value.equals("bg6")) {
            binding.rlMain.setBackgroundResource(R.drawable.bg);
        }

    }
}