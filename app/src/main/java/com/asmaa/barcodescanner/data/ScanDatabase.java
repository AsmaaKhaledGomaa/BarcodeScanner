package com.asmaa.barcodescanner.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScanResult.class}, version = 1)
public abstract class ScanDatabase extends RoomDatabase {

    private static ScanDatabase instance;

    public abstract ScanResultDao scanResultDao();

    public static synchronized ScanDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ScanDatabase.class, "scan_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
