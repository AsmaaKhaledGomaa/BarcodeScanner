package com.asmaa.barcodescanner.domain;

import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

public class ScanUseCase {
    public void startScan(Fragment fragment) {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(fragment);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  // Handle both QR and barcode formats
        integrator.setPrompt("Scan a QR or Barcode");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }
}

