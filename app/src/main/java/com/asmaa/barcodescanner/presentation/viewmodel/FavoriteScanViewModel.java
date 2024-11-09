package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.database.FavoriteDatabase;
import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.domain.repo.FavoriteScanRepository;
import com.asmaa.barcodescanner.domain.usecase.FavoriteScanUseCase;

import java.util.List;

public class FavoriteScanViewModel extends AndroidViewModel {

    private final FavoriteScanUseCase favoriteScanUseCase;
    private final LiveData<List<FavoriteScan>> allFavorites;

    public FavoriteScanViewModel(@NonNull Application application) {
        super(application);
        FavoriteScanRepository repository = new FavoriteScanRepository(FavoriteDatabase.getDatabase(application).favoriteScanDao());
        favoriteScanUseCase = new FavoriteScanUseCase(repository);
        allFavorites = favoriteScanUseCase.getAllFavorites();
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return allFavorites;
    }

    public void addFavorite(FavoriteScan favoriteScan) {
        favoriteScanUseCase.addFavorite(favoriteScan);
    }

    public void deleteFavoriteById(int id) {
        favoriteScanUseCase.deleteFavoriteById(id);
    }
}




