package com.asmaa.barcodescanner.presentation.fragment;

import static com.asmaa.barcodescanner.utils.Constants.CAMERA_PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.asmaa.barcodescanner.R;
import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.databinding.FragmentScanBinding;
import com.asmaa.barcodescanner.presentation.viewmodel.FavoriteScanViewModel;
import com.asmaa.barcodescanner.presentation.viewmodel.ScanViewModel;
import com.asmaa.barcodescanner.utils.PermissionManager;
import com.asmaa.barcodescanner.utils.PermissionUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    private ScanViewModel scanViewModel;
    private FavoriteScanViewModel favoriteScanViewModel;
    private PermissionManager permissionManager;
    private boolean isFavorite = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        scanViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        favoriteScanViewModel = new ViewModelProvider(this).get(FavoriteScanViewModel.class);
        permissionManager = new PermissionManager(requireContext());

        setupObservers();
        return binding.getRoot();
    }

    private void setupObservers() {
        scanViewModel.isPermissionGranted().observe(getViewLifecycleOwner(), isGranted -> {
            if (isGranted) {
                scanViewModel.startScan(this);
            } else {
                checkPermissionDenialCount();
            }
        });

        scanViewModel.getLatestScanResult().observe(getViewLifecycleOwner(), latestScan -> {
            if (latestScan != null && latestScan.getResult() != null && !latestScan.getResult().isEmpty()) {
                // Valid scan data, update UI
                binding.scanResult.setText(latestScan.getResult());
                binding.scanType.setText("Scan Type: " + latestScan.getType());
                isFavorite = latestScan.isFavorite();
                updateFavoriteButtonState();
                binding.favoriteButton.setEnabled(true); // Enable button if scan is valid
                Log.d("Scansaved", "ScanFragment: Latest scan: " + latestScan.getResult());

            } else {
                // No valid scan data yet
                binding.favoriteButton.setEnabled(false);
                Log.e("Scansaved", "ScanFragment: No scan result available.");

                Toast.makeText(requireContext(), "No valid scan available yet", Toast.LENGTH_SHORT).show();
            }
        });


        binding.favoriteButton.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateFavoriteButtonState();
        });
    }

    private void updateFavoriteButtonState() {
        int color = isFavorite ? R.color.red : R.color.green;
        binding.favoriteButton.setColorFilter(ContextCompat.getColor(requireContext(), color), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.scanButton.setOnClickListener(v -> scanViewModel.checkCameraPermission());
    }

    private void checkPermissionDenialCount() {
        int deniedCount = permissionManager.getCameraPermissionDenialCount();

        if (deniedCount >= 3) {
            PermissionUtils.openAppSettings(this);
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
            Toast.makeText(requireContext(), "Camera permission is needed to scan QR/barcodes", Toast.LENGTH_SHORT).show();
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scanViewModel.startScan(this);
                permissionManager.resetCameraPermissionDenialCount();
            } else {
                handlePermissionDenied();
            }
        }
    }

    private void handlePermissionDenied() {
        permissionManager.incrementCameraPermissionDenialCount();
        Toast.makeText(requireContext(), "Camera permission is required to scan QR/barcodes", Toast.LENGTH_SHORT).show();

        if (permissionManager.getCameraPermissionDenialCount() >= 2) {
            PermissionUtils.openAppSettings(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        scanViewModel.processScanResult(result);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
