package com.asmaa.barcodescanner.presentation.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.databinding.ItemScanHistoryBinding;
import com.asmaa.barcodescanner.presentation.listenner.OnDeleteClickListener;


public class ScanHistoryAdapter extends ListAdapter<ScanResult, ScanHistoryAdapter.ScanHistoryViewHolder> {

    private final OnDeleteClickListener onDeleteClickListener;

    public ScanHistoryAdapter(OnDeleteClickListener onDeleteClickListener) {
        super(DIFF_CALLBACK);
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static final DiffUtil.ItemCallback<ScanResult> DIFF_CALLBACK = new DiffUtil.ItemCallback<ScanResult>() {
        @Override
        public boolean areItemsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ScanHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemScanHistoryBinding binding = ItemScanHistoryBinding.inflate(inflater, parent, false);
        return new ScanHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanHistoryViewHolder holder, int position) {
        ScanResult scanResult = getItem(position);
        holder.bind(scanResult);
        holder.binding.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(scanResult.getId());
            }
        });
    }

    public static class ScanHistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemScanHistoryBinding binding;

        public ScanHistoryViewHolder(ItemScanHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ScanResult scanResult) {
            binding.setHistoryScan(scanResult);
            binding.executePendingBindings();
        }
    }
}
