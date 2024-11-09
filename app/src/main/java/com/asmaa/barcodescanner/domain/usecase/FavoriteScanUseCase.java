package com.asmaa.barcodescanner.domain.usecase;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.domain.repo.FavoriteScanRepository;

import java.util.List;

public class FavoriteScanUseCase {

    private final FavoriteScanRepository favoriteScanRepository;

    public FavoriteScanUseCase(FavoriteScanRepository favoriteScanRepository) {
        this.favoriteScanRepository = favoriteScanRepository;
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return favoriteScanRepository.getAllFavorites();
    }

    public void addFavorite(FavoriteScan favoriteScan) {
        favoriteScanRepository.insert(favoriteScan);
    }

    public void deleteFavoriteById(int id) {
        favoriteScanRepository.deleteById(id);
    }
}
