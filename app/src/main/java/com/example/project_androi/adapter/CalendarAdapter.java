package com.example.project_androi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import com.example.project_androi.R;
import com.example.project_androi.model.ItemDay;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemDay> mItemDays;

    public CalendarAdapter(Context context, List<ItemDay> itemDays) {
        mContext = context;
        mItemDays = itemDays;
    }

    @Override
    public int getCount() {
        return mItemDays.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_day, null);
        }

        TextView lunarDayTextView = (TextView) view.findViewById(R.id.day_lunar_textview);
        TextView solarDayTextView = (TextView) view.findViewById(R.id.day_solar_textview);
        LinearLayout itemDayLinearLayout = (LinearLayout) view.findViewById(R.id.item_day);
        // Thiết lập giá trị cho TextViews tương ứng với ngày hiện tại
        ItemDay itemDay = mItemDays.get(position);
        if(itemDay.getDay() == 0){
            lunarDayTextView.setText("");
            solarDayTextView.setText("");
            return view;
        }
        // Nếu là ngày hiện tại, đặt màu nền và màu chữ khác
        Calendar calendar = Calendar.getInstance();
        if(itemDay.getDay() == calendar.get(Calendar.DAY_OF_MONTH) &&
                itemDay.getMonth() == calendar.get(Calendar.MONTH)+1 &&
                itemDay.getYear() == calendar.get(Calendar.YEAR)){
            lunarDayTextView.setTextColor(mContext.getResources().getColor(R.color.white));
            solarDayTextView.setTextColor(mContext.getResources().getColor(R.color.white));
            itemDayLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }else{
            lunarDayTextView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            solarDayTextView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
        lunarDayTextView.setText(itemDay.getLunarDay()+"");
        solarDayTextView.setText(itemDay.getDay()+"");

        if(itemDay.isEvent()){
            solarDayTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        //onclick
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemDayClickListener listener = (OnItemDayClickListener) mContext;
                listener.onItemDayClick(itemDay);
            }
        });
        return view;
    }

    //interface
    public interface OnItemDayClickListener {
        void onItemDayClick(ItemDay itemDay);
    }
}
