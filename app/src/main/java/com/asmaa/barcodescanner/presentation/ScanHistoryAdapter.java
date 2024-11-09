package com.asmaa.barcodescanner.presentation;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.asmaa.barcodescanner.R;
import com.asmaa.barcodescanner.data.entity.ScanResult;

public class ScanHistoryAdapter extends ListAdapter<ScanResult, ScanHistoryAdapter.ScanHistoryViewHolder> {

    public ScanHistoryAdapter() {
        super(new DiffUtil.ItemCallback<ScanResult>() {
            @Override
            public boolean areItemsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ScanHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scan_history, parent, false);
        return new ScanHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanHistoryViewHolder holder, int position) {
        ScanResult scanResult = getItem(position);
        holder.bind(scanResult);
    }

    public static class ScanHistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView scanResultText;
        private final TextView scanTypeText;

        public ScanHistoryViewHolder(View itemView) {
            super(itemView);
            scanResultText = itemView.findViewById(R.id.scan_result);
            scanTypeText = itemView.findViewById(R.id.scan_type);
        }

        public void bind(ScanResult scanResult) {
            scanResultText.setText(scanResult.getResult());
            scanTypeText.setText("Type: " + scanResult.getType());
        }
    }
}
