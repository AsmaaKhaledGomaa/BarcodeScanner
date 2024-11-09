package com.asmaa.barcodescanner.domain.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.database.FavoriteDatabase;
import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.data.dao.FavoriteScanDao;

import java.util.List;

public class FavoriteScanRepository {

    private final FavoriteScanDao favoriteScanDao;

    public FavoriteScanRepository(Application application) {
        FavoriteDatabase db = FavoriteDatabase.getDatabase(application);
        favoriteScanDao = db.favoriteScanDao();
    }

    public LiveData<FavoriteScan> getFavoriteScanByResult(String scanResult) {
        return favoriteScanDao.getFavoriteScanByResult(scanResult);
    }

    public void insert(FavoriteScan favoriteScan) {
        new Thread(() -> favoriteScanDao.insert(favoriteScan)).start();
    }

    public void deleteById(int id) {
        new Thread(() -> favoriteScanDao.deleteById(id)).start();
    }

    public void delete() {
        new Thread(() -> favoriteScanDao.delete()).start();
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return favoriteScanDao.getAllFavorites();
    }
}
