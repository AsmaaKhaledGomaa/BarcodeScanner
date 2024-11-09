package com.asmaa.barcodescanner.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scan_result")
public class ScanResult {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String result; // The content of the scan (QR code or barcode result)
    private String type;   // The type of scan (QR, Barcode, etc.)

    // Constructor
    public ScanResult(String result, String type) {
        this.result = result;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
