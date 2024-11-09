package com.asmaa.barcodescanner.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScanResultDao {
    @Insert
    void insert(ScanResult scanResult);

    @Delete
    void delete(ScanResult scanResult);

    @Query("DELETE FROM scan_result")
    void deleteAll();  // Delete all previous scans

    @Query("SELECT * FROM scan_result ORDER BY id DESC LIMIT 1")
    LiveData<ScanResult> getLatestScanResult();  // Get only the latest scan result
}

