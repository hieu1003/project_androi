package com.example.project_androi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.project_androi.R;
import com.example.project_androi.databinding.ItemEventBinding;
import com.example.project_androi.model.event;

public class adapterEvent extends RecyclerView.Adapter<adapterEvent.ViewHolder> {

    private Context context;
    private List<event> listEvent;

    public adapterEvent(Context context, List<event> listEvent) {
        this.context = context;
        this.listEvent = listEvent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        event event = listEvent.get(position);
        holder.binding.tvTitle.setText(event.getTitle());
        holder.binding.tvTime.setText(event.getTime()+"-"+event.getDay()+"/"+event.getMonth()+"/"+event.getYear());
        holder.binding.tvNote.setText(event.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof OnItemClickListener) {
                    ((OnItemClickListener)context).onItemClick(event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemEventBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemEventBinding.bind(itemView);
        }
    }


    //interface
    public interface OnItemClickListener {
        void onItemClick(event event);
    }
}
