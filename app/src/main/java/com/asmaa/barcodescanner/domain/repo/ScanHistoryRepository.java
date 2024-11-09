package com.asmaa.barcodescanner.domain.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.database.ScanDatabase;
import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.data.dao.ScanResultDao;

import java.util.List;

public class ScanHistoryRepository {
    private final ScanResultDao scanResultDao;

    public ScanHistoryRepository(Application application) {
        ScanDatabase db = ScanDatabase.getInstance(application);
        scanResultDao = db.scanResultDao();
    }

    public LiveData<List<ScanResult>> getAllScanResults() {
        return scanResultDao.getAllScanResults();
    }
}

