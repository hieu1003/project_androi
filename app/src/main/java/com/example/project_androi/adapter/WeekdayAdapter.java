package com.example.project_androi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import phcom.edu.lichvannien.R;

public class WeekdayAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mWeekdays;

    public WeekdayAdapter(Context context) {
        mContext = context;
        mWeekdays = context.getResources().getStringArray(R.array.weekdays);
    }

    @Override
    public int getCount() {
        return mWeekdays.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // Nếu chưa có view, tạo một TextView mới
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            textView.setPadding(8, 8, 8, 8);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.GREEN);
            textView.setTextColor(Color.WHITE);
        } else {
            // Nếu đã có view, tái sử dụng nó
            textView = (TextView) convertView;
        }

        // Thiết lập nội dung của TextView
        if(position == 0) {
            textView.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        }

        textView.setText(mWeekdays[position]);

        return textView;
    }
}
