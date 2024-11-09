package com.asmaa.barcodescanner.domain.repo;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.data.dao.FavoriteScanDao;

import java.util.List;

public class FavoriteScanRepository {

    private final FavoriteScanDao favoriteScanDao;

    public FavoriteScanRepository(FavoriteScanDao favoriteScanDao) {
        this.favoriteScanDao = favoriteScanDao;
    }

    public LiveData<List<FavoriteScan>> getAllFavorites() {
        return favoriteScanDao.getAllFavorites();
    }

    public void insert(FavoriteScan favoriteScan) {
        new Thread(() -> favoriteScanDao.insert(favoriteScan)).start();
    }

    public void deleteById(int id) {
        new Thread(() -> favoriteScanDao.deleteById(id)).start();
    }
}

