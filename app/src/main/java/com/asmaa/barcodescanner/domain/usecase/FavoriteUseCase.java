package com.asmaa.barcodescanner.domain.usecase;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.domain.repo.FavoriteScanRepository;

import java.util.List;

public class FavoriteUseCase {

    private final FavoriteScanRepository repository;

    public FavoriteUseCase(FavoriteScanRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return repository.getAllFavorites();
    }

    public void addFavorite(FavoriteScan favoriteScan) {
        repository.insert(favoriteScan);
    }

    public void deleteFavoriteById(int id) {
        repository.deleteById(id);
    }

    public LiveData<FavoriteScan> getFavoriteScanByResult(String scanResult) {
        return repository.getFavoriteScanByResult(scanResult);
    }
}
