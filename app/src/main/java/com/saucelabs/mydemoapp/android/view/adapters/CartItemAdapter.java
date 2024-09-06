package com.saucelabs.mydemoapp.android.view.adapters;

import static com.saucelabs.mydemoapp.android.utils.Network.fetch;

import android.annotation.SuppressLint;
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
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
		CartItemModel model = list.get(position);
		ProductModel productModel = model.getProductModel();
		holder.binding.titleTV.setText(productModel.getTitle());
		holder.binding.priceTV.setText(productModel.getCurrency() + " " + productModel.getPrice());
		holder.handleRating(productModel.getRating());
		holder.binding.noTV.setText(String.valueOf(model.getNumberOfProduct()));
		holder.binding.colorIV.setImageResource(model.getColor());
		holder.binding.productIV.setImageResource(model.getProductModel().getImageVal());

		if (editable) {
			holder.binding.addToCartLL.setVisibility(View.VISIBLE);
		} else {
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

				// For testing purposes, when removing an item from the shopping cart
				// we're blocking the ui thread for 2 seconds. This will show up in both
				// Sauce Labs and TestFairy as high latency in responsiveness of UI thread.
				long end = System.currentTimeMillis() + 5000;
				while (System.currentTimeMillis() < end) {
					// try again and again!
				}
			}
		});

		holder.binding.plusIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				model.setNumberOfProduct(model.getNumberOfProduct() + 1);
				holder.binding.noTV.setText(String.valueOf(model.getNumberOfProduct()));
				clickListener.OnClick(position, 1);
			}
		});

		holder.binding.minusIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				model.setNumberOfProduct(model.getNumberOfProduct() - 1);
				if (model.getNumberOfProduct() < 1) {
					removeItem(model);
				} else {
					holder.binding.noTV.setText(String.valueOf(model.getNumberOfProduct()));
				}

				clickListener.OnClick(position, 1);
			}
		});
	}

	private void removeItem(CartItemModel cartItemModel) {
		for (int pos = 0; pos < ST.cartItemList.size(); pos++) {
			if (cartItemModel.getProductModel().getId() == ST.cartItemList.get(pos).getProductModel().getId()) {
				ST.cartItemList.remove(cartItemModel);
				fetch("https://my-demo-app.net/api/remove-item?id=" + cartItemModel.getProductModel().getId());
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		ItemMyCartBinding binding;

		public ViewHolder(@NonNull ItemMyCartBinding itemView) {
			super(itemView.getRoot());
			this.binding = itemView;
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
