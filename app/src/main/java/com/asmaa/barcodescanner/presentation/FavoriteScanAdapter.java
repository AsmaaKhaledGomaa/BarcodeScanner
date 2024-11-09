package com.asmaa.barcodescanner.presentation;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.databinding.ItemFavoriteScanBinding;

public class FavoriteScanAdapter extends ListAdapter<FavoriteScan, FavoriteScanAdapter.FavoriteScanViewHolder> {

    // Constructor to use DIFF_CALLBACK for detecting changes
    public FavoriteScanAdapter() {
        super(DIFF_CALLBACK);
    }

    // DiffUtil callback to optimize RecyclerView updates
    public static final DiffUtil.ItemCallback<FavoriteScan> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteScan>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteScan oldItem, @NonNull FavoriteScan newItem) {
            return oldItem.getScanResult().equals(newItem.getScanResult()); // Unique identifier check
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull FavoriteScan oldItem, @NonNull FavoriteScan newItem) {
            return oldItem.equals(newItem); // Compare contents of the items
        }
    };

    @NonNull
    @Override
    public FavoriteScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout using data binding
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFavoriteScanBinding binding = ItemFavoriteScanBinding.inflate(inflater, parent, false);
        return new FavoriteScanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteScanViewHolder holder, int position) {
        // Get the item at the given position and bind it to the ViewHolder
        FavoriteScan favoriteScan = getItem(position);
        holder.bind(favoriteScan);
    }

    // ViewHolder class for binding data to views
    public static class FavoriteScanViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteScanBinding binding;

        public FavoriteScanViewHolder(ItemFavoriteScanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FavoriteScan favoriteScan) {
            // Bind the FavoriteScan object to the layout using DataBinding
            binding.setFavoriteScan(favoriteScan);
            binding.executePendingBindings(); // Ensure binding happens immediately
        }
    }
}
