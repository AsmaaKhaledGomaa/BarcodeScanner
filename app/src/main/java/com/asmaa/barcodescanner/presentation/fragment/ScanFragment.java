package com.asmaa.barcodescanner.presentation.fragment;

import static com.asmaa.barcodescanner.utils.Constants.CAMERA_PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.asmaa.barcodescanner.R;
import com.asmaa.barcodescanner.data.FavoriteScan;
import com.asmaa.barcodescanner.data.ScanResult;
import com.asmaa.barcodescanner.databinding.FragmentScanBinding;
import com.asmaa.barcodescanner.presentation.viewmodel.FavoriteScanViewModel;
import com.asmaa.barcodescanner.presentation.viewmodel.ScanViewModel;
import com.asmaa.barcodescanner.utils.PermissionManager;
import com.asmaa.barcodescanner.utils.PermissionUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    private ScanViewModel viewModel;
    private PermissionManager permissionManager;
    boolean isFavorite = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        permissionManager = new PermissionManager(requireContext());

        setupObservers();
        return binding.getRoot();
    }

    private void setupObservers() {
        viewModel.isPermissionGranted().observe(getViewLifecycleOwner(), isGranted -> {
            if (isGranted) {
                viewModel.startScan(this);  // Start scanning after permission is granted
            } else {
                checkPermissionDenialCount();
            }
        });

        // Observe the latest scan result and scan type
        viewModel.getLatestScanResult().observe(getViewLifecycleOwner(), latestScan -> {
            if (latestScan != null) {
                binding.scanResult.setText(latestScan.getResult());  // Display scanned result
                binding.scanType.setText("Scan Type: " + latestScan.getType());  // Display scan type
            }
        });

        binding.favoriteButton.setOnClickListener(v -> {
            // Get scan result and scan type from the UI
            String scanResult = binding.scanResult.getText().toString();
            String scanType = binding.scanType.getText().toString().replace("Scan Type: ", "");

            // Check if scan result or type is empty
            if (scanResult.isEmpty() || scanType.isEmpty()) {
                Toast.makeText(requireContext(), "Scan result or type is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new FavoriteScan object with the scanned result and type
            FavoriteScan favoriteScan = new FavoriteScan(scanResult, scanType);

            // Insert or remove the FavoriteScan object into the Room database using the ViewModel
            FavoriteScanViewModel favoriteScanViewModel = new ViewModelProvider(requireActivity()).get(FavoriteScanViewModel.class);

            if (!isFavorite) {
                // Add to favorites
                favoriteScanViewModel.insert(favoriteScan);
                // Change button color to red
                ((ImageButton) v).setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.SRC_IN);
                // Set `isFavorite` to true
                isFavorite = true;
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                // Query to find the ID of the scan in the database by scanResult
                favoriteScanViewModel.getFavoriteScanByResult(scanResult).observe(getViewLifecycleOwner(), favoriteScanInDb -> {
                    if (favoriteScanInDb != null) {
                        // If the scan exists, delete it by its ID
                        favoriteScanViewModel.delete();
                        // Change button color to green
                        ((ImageButton) v).setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.SRC_IN);
                        // Set `isFavorite` to false
                        isFavorite = false;
                        Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.scanButton.setOnClickListener(v -> viewModel.checkCameraPermission());
    }

    private void checkPermissionDenialCount() {
        int deniedCount = permissionManager.getCameraPermissionDenialCount();

        if (deniedCount >= 3) {
            // Permission denied three times, open settings
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
                viewModel.startScan(this);  // Start scanning when permission is granted
                permissionManager.resetCameraPermissionDenialCount();  // Reset counter
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
        viewModel.processScanResult(result);  // Process the result after scanning
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
