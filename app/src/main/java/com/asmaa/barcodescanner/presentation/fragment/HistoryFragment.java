package com.asmaa.barcodescanner.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmaa.barcodescanner.R;
import com.asmaa.barcodescanner.presentation.adapter.ScanHistoryAdapter;
import com.asmaa.barcodescanner.presentation.listenner.OnDeleteClickListener;
import com.asmaa.barcodescanner.presentation.viewmodel.HistoryViewModel;

public class HistoryFragment extends Fragment implements OnDeleteClickListener {
    private RecyclerView recyclerView;
    private ScanHistoryAdapter adapter;
    private HistoryViewModel historyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScanHistoryAdapter(this);
        recyclerView.setAdapter(adapter);

        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // Observe the LiveData from the ViewModel
        historyViewModel.getScanHistory().observe(getViewLifecycleOwner(), scanResults -> {
            adapter.submitList(scanResults);
        });

        return root;
    }

    @Override
    public void onDeleteClick(int id) {
        historyViewModel.deleteHistoryById(id);

    }
}
