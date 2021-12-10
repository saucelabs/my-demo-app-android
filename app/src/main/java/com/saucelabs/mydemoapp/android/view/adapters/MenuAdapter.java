package com.saucelabs.mydemoapp.android.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.MenuItemBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<String>  list;
    private Context context;
    private OnItemClickListener listener;

    public MenuAdapter(List<String> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MenuItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.menu_item, parent, false);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_grid_recent_transfer2, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position == 0){
            holder.binding.topViewTV.setVisibility(View.VISIBLE);
        }else{
            holder.binding.topViewTV.setVisibility(View.GONE);
        }

        if(position == 0 || position == 4){
            holder.binding.itemV.setVisibility(View.VISIBLE);
        }else{
            holder.binding.itemV.setVisibility(View.INVISIBLE);
        }

        holder.binding.itemTV.setText(list.get(position));

        holder.binding.itemTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(position,-1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MenuItemBinding binding;

        public ViewHolder(@NonNull MenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
