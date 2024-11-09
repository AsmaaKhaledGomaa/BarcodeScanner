package com.asmaa.barcodescanner.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.asmaa.barcodescanner.data.entity.ScanResult;

import java.util.List;

@Dao
public interface ScanResultDao {
    @Insert
    void insert(ScanResult scanResult);

    @Delete
    void delete(ScanResult scanResult);

    @Query("SELECT * FROM scan_result ORDER BY id DESC LIMIT 1")
    LiveData<ScanResult> getLatestScanResult();

    @Query("UPDATE scan_result SET isFavorite = :isFavorite WHERE id = :scanId")
    void updateFavoriteState(int scanId, boolean isFavorite);

    @Query("SELECT * FROM scan_result ORDER BY id DESC")
    LiveData<List<ScanResult>> getAllScanResults();
}

