package com.saucelabs.mydemoapp.android.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ItemColorBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.ColorModel;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {

    Activity mAct;
    List<ColorModel> list;
    OnItemClickListener clickListener;
    SingletonClass ST;

    int selectedPos = 0;

    public ColorsAdapter(Activity mAct, List<ColorModel> list, OnItemClickListener clickListener) {
        this.mAct = mAct;
        this.list = list;
        ST = SingletonClass.getInstance();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemColorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_color, parent, false);
        return new ViewHolder(binding);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorModel model = list.get(position);
        holder.binding.colorIV.setImageResource(model.getColorImg());

        if(selectedPos == position){
            holder.binding.aroundIV.setImageResource(holder.getAroundIV(model));
            holder.binding.aroundIV.setVisibility(View.VISIBLE);
        }else{
            holder.binding.aroundIV.setVisibility(View.INVISIBLE);
        }

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position;
                notifyDataSetChanged();
                clickListener.OnClick(position , -1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ItemColorBinding binding;

        public ViewHolder(@NonNull ItemColorBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }

        private int getAroundIV(ColorModel model){
            if(model.getColorValue() == ST.BLACK){
                return R.drawable.ic_black_circle_side;
            }else if(model.getColorValue() == ST.RED){
                return R.drawable.ic_red_circle_side;
            }else if(model.getColorValue() == ST.GRAY){
                return R.drawable.ic_gray_circle_side;
            }else if(model.getColorValue() == ST.BLUE){
                return R.drawable.ic_blue_circle_side;
            }else{
                return 0;
            }
        }

    }


}
