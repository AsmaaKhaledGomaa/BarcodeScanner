package com.asmaa.barcodescanner.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.asmaa.barcodescanner.R;
import com.asmaa.barcodescanner.databinding.ActivityMainBinding;
import com.asmaa.barcodescanner.presentation.fragment.FavoriteFragment;
import com.asmaa.barcodescanner.presentation.fragment.HistoryFragment;
import com.asmaa.barcodescanner.presentation.fragment.ScanFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set default fragment
        replaceFragment(new ScanFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    replaceFragment(new ScanFragment());
                    break;
                case R.id.navigation_favorite:
                    replaceFragment(new FavoriteFragment());
                    break;
                case R.id.navigation_history:
                    replaceFragment(new HistoryFragment()) ;
                    break;
            }
            return true;
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
