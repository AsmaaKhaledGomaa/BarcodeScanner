package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.domain.usecase.ScanHistoryUseCase;
import com.asmaa.barcodescanner.domain.repo.ScanHistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final ScanHistoryUseCase scanHistoryUseCase;
    private final LiveData<List<ScanResult>> scanHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        ScanHistoryRepository repository = new ScanHistoryRepository(application);
        scanHistoryUseCase = new ScanHistoryUseCase(repository);
        scanHistory = scanHistoryUseCase.execute();
    }

    public LiveData<List<ScanResult>> getScanHistory() {
        return scanHistory;
    }

    public void deleteHistoryById(int id) {
        scanHistoryUseCase.deleteHistoryById(id);
    }
}
