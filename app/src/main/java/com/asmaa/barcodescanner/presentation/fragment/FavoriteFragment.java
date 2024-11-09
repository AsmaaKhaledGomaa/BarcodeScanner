package com.asmaa.barcodescanner.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.asmaa.barcodescanner.databinding.FragmentFavoriteBinding;
import com.asmaa.barcodescanner.presentation.adapter.FavoriteScanAdapter;
import com.asmaa.barcodescanner.presentation.viewmodel.FavoriteScanViewModel;


public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteScanViewModel favoriteScanViewModel;
    private FavoriteScanAdapter favoriteScanAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        favoriteScanViewModel = new ViewModelProvider(this).get(FavoriteScanViewModel.class);

        setupRecyclerView();
        observeFavorites();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        favoriteScanAdapter = new FavoriteScanAdapter();
        binding.favoriteScanRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.favoriteScanRecyclerview.setAdapter(favoriteScanAdapter);
    }

    private void observeFavorites() {
        favoriteScanViewModel.getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null) {
                favoriteScanAdapter.submitList(favorites);
                favoriteScanAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

