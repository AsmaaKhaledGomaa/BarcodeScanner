package com.asmaa.barcodescanner.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;

import java.util.List;

@Dao
public interface FavoriteScanDao {
    @Insert
    void insert(FavoriteScan favoriteScan);

    @Delete
    void delete(FavoriteScan favoriteScan);

    @Query("SELECT * FROM favorite_scans")
    LiveData<List<FavoriteScan>> getAllFavorites();

    @Query("DELETE FROM favorite_scans WHERE id = :id")
    void deleteById(int id);
}

