package com.saucelabs.mydemoapp.android.utils.base;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

import java.util.List;




public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Activity mAct;
    public SingletonClass ST;
    public OnItemClickListener listener;
    public List<BaseModel> list;

    protected BaseAdapter(Activity mAct, List<BaseModel> list, OnItemClickListener listener) {
        this.mAct = mAct;
        this.ST = SingletonClass.getInstance();
        this.listener = listener;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
