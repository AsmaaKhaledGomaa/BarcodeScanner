package com.asmaa.barcodescanner.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.asmaa.barcodescanner.data.ScanResult;
import com.asmaa.barcodescanner.domain.ScanRepository;
import com.asmaa.barcodescanner.domain.ScanUseCase;
import com.asmaa.barcodescanner.presentation.fragment.ScanFragment;
import com.asmaa.barcodescanner.utils.PermissionUtils;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

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

    // Fetch the latest scan result from the repository
    public LiveData<ScanResult> getLatestScanResult() {
        return scanRepository.getLatestScanResult();
    }

    public LiveData<Boolean> isPermissionGranted() {
        return permissionGranted;
    }

    public LiveData<String> getScannedResult() {
        return scannedResult;
    }

    public LiveData<String> getScanType() {
        return scanType;
    }


    public void checkCameraPermission() {
        boolean isGranted = PermissionUtils.isCameraPermissionGranted(getApplication());
        permissionGranted.setValue(isGranted);
    }

    public void startScan(ScanFragment fragment) {
        if (scanUseCase != null) {  // Check if it's initialized
            // Start scanning without specifying a type
            scanUseCase.startScan(fragment);
        } else {
            // Handle the case where scanUseCase is not initialized
            Log.e("ScanViewModel", "scanUseCase is not initialized.");
        }
    }

    public void processScanResult(IntentResult result) {
        if (result != null && result.getContents() != null) {
            String scanResultContent = result.getContents();
            scannedResult.setValue(scanResultContent);  // Set scan result

            // Insert the scan result into the database
            ScanResult scanResult = new ScanResult(scanResultContent, scanType.getValue());
            scanRepository.insertLatestScanResult(scanResult);

            // Determine scan type based on result format
            String formatName = result.getFormatName();
            if ("QR_CODE".equals(formatName)) {
                scanType.setValue("QR Code");
            } else if ("EAN_13".equals(formatName) || "UPC_A".equals(formatName) ||
                    "EAN_8".equals(formatName) || "UPC_E".equals(formatName) ||
                    "CODE_39".equals(formatName) || "CODE_128".equals(formatName) ||
                    "ITF".equals(formatName) || "PDF_417".equals(formatName) ||
                    "AZTEC".equals(formatName) || "DATA_MATRIX".equals(formatName)) {
                scanType.setValue("Barcode");
            } else {
                scanType.setValue("Unknown");
            }
        } else {
            scannedResult.setValue("Scan canceled or failed");
            scanType.setValue("Unknown");
        }
    }


}