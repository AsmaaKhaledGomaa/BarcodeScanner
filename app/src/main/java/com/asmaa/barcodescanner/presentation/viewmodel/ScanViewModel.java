package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.domain.repo.ScanRepository;
import com.asmaa.barcodescanner.domain.usecase.ScanUseCase;
import com.asmaa.barcodescanner.presentation.fragment.ScanFragment;
import com.asmaa.barcodescanner.utils.PermissionUtils;
import com.google.zxing.integration.android.IntentResult;

public class ScanViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> permissionGranted = new MutableLiveData<>();
    private final MutableLiveData<String> scannedResult = new MutableLiveData<>();
    private final MutableLiveData<String> scanType = new MutableLiveData<>();
    private final ScanRepository scanRepository;
    private final ScanUseCase scanUseCase;

    public ScanViewModel(@NonNull Application application) {
        super(application);
        scanUseCase = new ScanUseCase();
        scanRepository = new ScanRepository(application);
    }

    public LiveData<String> getScannedResult() {
        return scannedResult;
    }

    public LiveData<String> getScanType() {
        return scanType;
    }

    public LiveData<ScanResult> getLatestScanResult() {
        return scanRepository.getLatestScanResult();
    }

    public LiveData<Boolean> isPermissionGranted() {
        return permissionGranted;
    }

    public void checkCameraPermission() {
        boolean isGranted = PermissionUtils.isCameraPermissionGranted(getApplication());
        permissionGranted.setValue(isGranted);
    }

    public void startScan(ScanFragment fragment) {
        if (scanUseCase != null) {
            scanUseCase.startScan(fragment);
        } else {
            Log.e("ScanViewModel", "scanUseCase is not initialized.");
        }
    }

    // Process the scan result and save it
    public void processScanResult(IntentResult result) {
        if (result != null && result.getContents() != null) {
            String scanResultContent = result.getContents();
            scannedResult.setValue(scanResultContent);

            String scanTypeValue = determineScanType(result);
            ScanResult scanResult = new ScanResult(scanResultContent, scanTypeValue, false);
            scanRepository.insertLatestScanResult(scanResult);

            scanType.setValue(scanTypeValue);
        } else {
            scannedResult.setValue("Scan canceled or failed");
            scanType.setValue("Unknown");
        }
    }

    private String determineScanType(IntentResult result) {
        String formatName = result.getFormatName();
        if ("QR_CODE".equals(formatName)) {
            return "QR Code";
        } else if ("EAN_13".equals(formatName) || "UPC_A".equals(formatName) ||
                "EAN_8".equals(formatName) || "UPC_E".equals(formatName) ||
                "CODE_39".equals(formatName) || "CODE_128".equals(formatName) ||
                "ITF".equals(formatName) || "PDF_417".equals(formatName) ||
                "AZTEC".equals(formatName) || "DATA_MATRIX".equals(formatName)) {
            return "Barcode";
        } else {
            return "Unknown";
        }
    }
}