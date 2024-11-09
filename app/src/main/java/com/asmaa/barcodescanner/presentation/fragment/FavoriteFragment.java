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
import com.asmaa.barcodescanner.presentation.FavoriteScanAdapter;
import com.asmaa.barcodescanner.presentation.viewmodel.FavoriteScanViewModel;

public class FavoriteFragment extends Fragment {

    private FavoriteScanViewModel favoriteScanViewModel;
    private RecyclerView recyclerView;
    private FavoriteScanAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        adapter = new FavoriteScanAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        favoriteScanViewModel = new ViewModelProvider(this).get(FavoriteScanViewModel.class);
        favoriteScanViewModel.getAllFavorites().observe(getViewLifecycleOwner(), favoriteScans -> {
            adapter.submitList(favoriteScans);
        });

        return root;
    }
}
