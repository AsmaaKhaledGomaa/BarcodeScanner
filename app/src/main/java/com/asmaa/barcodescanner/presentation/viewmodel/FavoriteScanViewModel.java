package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.domain.repo.FavoriteScanRepository;

import java.util.List;

public class FavoriteScanViewModel extends AndroidViewModel {
    private final FavoriteScanRepository favoriteScanRepository;
    private final LiveData<List<FavoriteScan>> allFavorites;

    public FavoriteScanViewModel(@NonNull Application application) {
        super(application);
        favoriteScanRepository = new FavoriteScanRepository(application);
        allFavorites = favoriteScanRepository.getAllFavorites();
    }

    public LiveData<FavoriteScan> getFavoriteScanByResult(String scanResult) {
        return favoriteScanRepository.getFavoriteScanByResult(scanResult);
    }


    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(FavoriteScan favoriteScan) {
        favoriteScanRepository.insert(favoriteScan);
    }

    public void deleteById(int id) {
        favoriteScanRepository.deleteById(id);
    }

    public void delete() {
        favoriteScanRepository.delete();
    }
}


