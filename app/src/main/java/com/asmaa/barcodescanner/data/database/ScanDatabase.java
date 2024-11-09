package com.asmaa.barcodescanner.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.data.entity.ScanResult;
import com.asmaa.barcodescanner.data.dao.ScanResultDao;

@Database(entities = {ScanResult.class}, version = 1, exportSchema = false)
public abstract class ScanDatabase extends RoomDatabase {

    private static volatile ScanDatabase INSTANCE;

    public abstract ScanResultDao scanResultDao();

    public static ScanDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScanDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ScanDatabase.class, "scan_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
