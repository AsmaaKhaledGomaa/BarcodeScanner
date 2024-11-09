package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.domain.repo.FavoriteScanRepository;
import com.asmaa.barcodescanner.domain.usecase.FavoriteUseCase;

import java.util.List;

public class FavoriteScanViewModel extends AndroidViewModel {

    private final FavoriteUseCase favoriteUseCase;
    private final LiveData<List<FavoriteScan>> allFavorites;

    public FavoriteScanViewModel(@NonNull Application application) {
        super(application);
        FavoriteScanRepository repository = new FavoriteScanRepository(application);
        favoriteUseCase = new FavoriteUseCase(repository);
        allFavorites = favoriteUseCase.getAllFavorites(); // Access favorites through use case
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return allFavorites;
    }

    public void addFavorite(FavoriteScan favoriteScan) {
        favoriteUseCase.addFavorite(favoriteScan);
    }

    public void deleteFavoriteById(int id) {
        favoriteUseCase.deleteFavoriteById(id);
    }

    public LiveData<FavoriteScan> getFavoriteScanByResult(String scanResult) {
        return favoriteUseCase.getFavoriteScanByResult(scanResult);
    }
}



