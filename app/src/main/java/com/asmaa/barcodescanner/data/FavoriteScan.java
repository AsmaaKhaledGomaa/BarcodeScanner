package com.asmaa.barcodescanner.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_scans")
public class FavoriteScan {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "scan_result")
    private String scanResult;

    @ColumnInfo(name = "scan_type")
    private String scanType;

    public FavoriteScan(String scanResult, String scanType) {
        this.scanResult = scanResult;
        this.scanType = scanType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }
}
