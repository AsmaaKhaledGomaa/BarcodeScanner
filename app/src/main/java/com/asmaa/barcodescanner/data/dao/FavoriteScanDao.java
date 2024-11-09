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

    @Query("SELECT * FROM favorite_scans WHERE scan_result = :scanResult LIMIT 1")
    LiveData<FavoriteScan> getFavoriteScanByResult(String scanResult);

    // Query to delete a FavoriteScan by its ID
    @Query("DELETE FROM favorite_scans WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM favorite_scans WHERE id = (SELECT MAX(id) FROM favorite_scans)")
    void delete();
}

