package com.asmaa.barcodescanner.domain.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.database.ScanDatabase;
import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.data.dao.ScanResultDao;

public class ScanRepository {
    private final ScanResultDao scanResultDao;

    public ScanRepository(Context context) {
        ScanDatabase database = ScanDatabase.getInstance(context);
        scanResultDao = database.scanResultDao();
    }

    public void insertLatestScanResult(ScanResult scanResult) {
        new Thread(() -> {
            scanResultDao.insert(scanResult);
            Log.d("Scansaved", " REPO : Scan result inserted: " + scanResult.getResult());
        }).start();
    }

    public void updateFavoriteState(int scanId, boolean isFavorite) {
        new Thread(() -> scanResultDao.updateFavoriteState(scanId, isFavorite)).start();
    }

    public LiveData<ScanResult> getLatestScanResult() {
        return scanResultDao.getLatestScanResult();
    }
}
