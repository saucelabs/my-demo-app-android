package com.saucelabs.mydemoapp.android.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ItemProductsBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
	Activity mAct;
	List<ProductModel> list;
	OnItemClickListener clickListener;
	SingletonClass ST;

	public ProductsAdapter(Activity mAct, List<ProductModel> list, OnItemClickListener clickListener) {
		this.mAct = mAct;
		this.list = list;
		ST = SingletonClass.getInstance();
		this.clickListener = clickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		ItemProductsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
			R.layout.item_products, parent, false);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
		ProductModel model = list.get(position);

		holder.binding.titleTV.setText(model.getTitle());
//		holder.binding.productIV.setImageBitmap(ST.getImage(model.getImage()));
		holder.binding.priceTV.setText(model.getCurrency() + " " + model.getPrice());
		holder.handleRating(model.getRating());
		holder.binding.productIV.setContentDescription(model.getTitle());
		holder.binding.productIV.setImageResource(model.getImageVal());

		holder.binding.productIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				clickListener.OnClick(position, 1);
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		ItemProductsBinding binding;

		public ViewHolder(@NonNull ItemProductsBinding itemView) {
			super(itemView.getRoot());
			this.binding = itemView;

			binding.rattingV.start1IV.setOnClickListener(this);
			binding.rattingV.start2IV.setOnClickListener(this);
			binding.rattingV.start3IV.setOnClickListener(this);
			binding.rattingV.start4IV.setOnClickListener(this);
			binding.rattingV.start5IV.setOnClickListener(this);
		}

		private void clearRating() {
			binding.rattingV.start1IV.setImageResource(R.drawable.ic_unselected_start);
			binding.rattingV.start2IV.setImageResource(R.drawable.ic_unselected_start);
			binding.rattingV.start3IV.setImageResource(R.drawable.ic_unselected_start);
			binding.rattingV.start4IV.setImageResource(R.drawable.ic_unselected_start);
			binding.rattingV.start5IV.setImageResource(R.drawable.ic_unselected_start);
		}

		private void handleRating(int rating) {
			clearRating();

			if (rating >= 1) {
				binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
			}

			if (rating >= 2) {
				binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
			}

			if (rating >= 3) {
				binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
			}

			if (rating >= 4) {
				binding.rattingV.start4IV.setImageResource(R.drawable.ic_selected_star);
			}

			if (rating >= 5) {
				binding.rattingV.start5IV.setImageResource(R.drawable.ic_selected_star);
			}
		}

		@Override
		public void onClick(View view) {
			ST.showReviewDialog(mAct);
			if (view.equals(binding.rattingV.start1IV)) {
				handleRating(1);
			} else if (view.equals(binding.rattingV.start2IV)) {
				handleRating(2);
			} else if (view.equals(binding.rattingV.start3IV)) {
				handleRating(3);
			} else if (view.equals(binding.rattingV.start4IV)) {
				handleRating(4);
			} else if (view.equals(binding.rattingV.start5IV)) {
				handleRating(5);
			}
		}
	}


}
