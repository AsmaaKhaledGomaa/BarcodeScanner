package com.asmaa.barcodescanner.domain.usecase;

import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.domain.repo.ScanHistoryRepository;

import java.util.List;

public class ScanHistoryUseCase {
    private final ScanHistoryRepository scanHistoryRepository;

    public ScanHistoryUseCase(ScanHistoryRepository scanHistoryRepository) {
        this.scanHistoryRepository = scanHistoryRepository;
    }

    public LiveData<List<ScanResult>> execute() {
        return scanHistoryRepository.getAllScanResults();
    }

    public void deleteHistoryById(int id) {
        scanHistoryRepository.deleteById(id);
    }
}

