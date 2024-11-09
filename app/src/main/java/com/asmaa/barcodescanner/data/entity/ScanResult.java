package com.asmaa.barcodescanner.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scan_result")
public class ScanResult {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String result;
    private String type;
    private boolean isFavorite;

    public ScanResult(String result, String type, boolean isFavorite) {
        this.result = result;
        this.type = type;
        this.isFavorite = isFavorite;
    }

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
