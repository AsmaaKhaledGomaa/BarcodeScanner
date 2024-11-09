package com.asmaa.barcodescanner.domain;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.ScanDatabase;
import com.asmaa.barcodescanner.data.ScanResult;
import com.asmaa.barcodescanner.data.ScanResultDao;

import java.util.List;

public class ScanRepository {
    private final ScanResultDao scanResultDao;

    public ScanRepository(Context context) {
        ScanDatabase database = ScanDatabase.getInstance(context);
        scanResultDao = database.scanResultDao();
    }

    // Insert a new scan result, but delete any existing scan before inserting
    public void insertLatestScanResult(ScanResult scanResult) {
        new Thread(() -> {
            scanResultDao.deleteAll();  // Delete previous scan results
            scanResultDao.insert(scanResult);  // Insert the latest scan result
        }).start();
    }



    // Get the latest scan result from the database
    public LiveData<ScanResult> getLatestScanResult() {
        return scanResultDao.getLatestScanResult();
    }
}
