package com.asmaa.barcodescanner.presentation.adapter;

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

    private final OnFavoriteClickListener onFavoriteClickListener;

    public FavoriteScanAdapter(OnFavoriteClickListener onFavoriteClickListener) {
        super(DIFF_CALLBACK);
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    public static final DiffUtil.ItemCallback<FavoriteScan> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteScan>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteScan oldItem, @NonNull FavoriteScan newItem) {
            return oldItem.getScanResult().equals(newItem.getScanResult());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull FavoriteScan oldItem, @NonNull FavoriteScan newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public FavoriteScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFavoriteScanBinding binding = ItemFavoriteScanBinding.inflate(inflater, parent, false);
        return new FavoriteScanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteScanViewHolder holder, int position) {
        FavoriteScan favoriteScan = getItem(position);
        holder.bind(favoriteScan);
        holder.binding.deleteButton.setOnClickListener(v -> {
            if (onFavoriteClickListener != null) {
                onFavoriteClickListener.onDeleteClick(favoriteScan.getId());
            }
        });
    }

    public static class FavoriteScanViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteScanBinding binding;

        public FavoriteScanViewHolder(ItemFavoriteScanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FavoriteScan favoriteScan) {
            binding.setFavoriteScan(favoriteScan);
            binding.executePendingBindings();
        }
    }

    public interface OnFavoriteClickListener {
        void onDeleteClick(int id);
    }
}

