package com.saucelabs.mydemoapp.android.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ItemMyCartBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.CartItemModel;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    Activity mAct;
    List<CartItemModel> list;
    OnItemClickListener clickListener;
    SingletonClass ST;
    boolean editable;

    public CartItemAdapter(Activity mAct, List<CartItemModel> list, boolean editable, OnItemClickListener clickListener) {
        this.mAct = mAct;
        this.list = list;
        ST = SingletonClass.getInstance();
        this.clickListener = clickListener;
        this.editable = editable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_my_cart, parent, false);
        return new ViewHolder(binding);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemModel model = list.get(position);
        ProductModel productModel = model.getProductModel();
        holder.binding.titleTV.setText(productModel.getTitle());
//        holder.binding.productIV.setImageBitmap( ST.getImage(model.getImage()));
        holder.binding.priceTV.setText(productModel.getCurrency() + " " + productModel.getPrice());
        holder.handleRatting(productModel.getRatting());
        holder.binding.noTV.setText(model.getNumberOfProduct() + "");
        holder.binding.colorIV.setImageResource(model.getColor());
        holder.binding.productIV.setImageResource(model.getProductModel().getImageVal());

        if(editable){
            holder.binding.addToCartLL.setVisibility(View.VISIBLE);
        }else{
            holder.binding.addToCartLL.setVisibility(View.GONE);
        }

        holder.binding.productIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.OnClick(position, 1);
            }
        });

        holder.binding.removeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(model);
                clickListener.OnClick(position, 1);
            }
        });

        holder.binding.plusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setNumberOfProduct(model.getNumberOfProduct() + 1);
                holder.binding.noTV.setText(model.getNumberOfProduct() + "");
                clickListener.OnClick(position, 1);
            }
        });

        holder.binding.minusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model.setNumberOfProduct(model.getNumberOfProduct() - 1);
                if (model.getNumberOfProduct() < 1) {
                    removeItem(model);
                } else
                    holder.binding.noTV.setText(model.getNumberOfProduct() + "");

                clickListener.OnClick(position, 1);
            }
        });

    }

    private void removeItem(CartItemModel cartItemModel) {
        for (int pos = 0; pos < ST.cartItemList.size(); pos++) {
            if (cartItemModel.getProductModel().getId() == ST.cartItemList.get(pos).getProductModel().getId()) {
                ST.cartItemList.remove(cartItemModel);
                ST.syncCartToTestFairy(mAct);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMyCartBinding binding;

        public ViewHolder(@NonNull ItemMyCartBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

//            binding.rattingV.start1IV.setOnClickListener(this);
//            binding.rattingV.start2IV.setOnClickListener(this);
//            binding.rattingV.start3IV.setOnClickListener(this);
//            binding.rattingV.start4IV.setOnClickListener(this);
//            binding.rattingV.start5IV.setOnClickListener(this);
        }

        private void handleRatting(int ratting) {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_unselected_start);
            binding.rattingV.start2IV.setImageResource(R.drawable.ic_unselected_start);
            binding.rattingV.start3IV.setImageResource(R.drawable.ic_unselected_start);
            binding.rattingV.start4IV.setImageResource(R.drawable.ic_unselected_start);
            binding.rattingV.start5IV.setImageResource(R.drawable.ic_unselected_start);

            if (ratting == 1) {
                binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
            } else if (ratting == 2) {
                binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
            } else if (ratting == 3) {
                binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
            } else if (ratting == 4) {
                binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start4IV.setImageResource(R.drawable.ic_selected_star);
            } else {
                binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start4IV.setImageResource(R.drawable.ic_selected_star);
                binding.rattingV.start5IV.setImageResource(R.drawable.ic_selected_star);
            }
        }

        @Override
        public void onClick(View view) {
            if (view.equals(binding.rattingV.start1IV)) {
                handleRatting(1);
            } else if (view.equals(binding.rattingV.start2IV)) {
                handleRatting(2);
            } else if (view.equals(binding.rattingV.start3IV)) {
                handleRatting(3);
            } else if (view.equals(binding.rattingV.start4IV)) {
                handleRatting(4);
            } else if (view.equals(binding.rattingV.start5IV)) {
                handleRatting(5);
            }
        }
    }


}
