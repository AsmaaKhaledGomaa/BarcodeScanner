package com.asmaa.barcodescanner.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.asmaa.barcodescanner.data.entity.FavoriteScan;
import com.asmaa.barcodescanner.data.dao.FavoriteScanDao;

@Database(entities = {FavoriteScan.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static volatile FavoriteDatabase INSTANCE;

    public abstract FavoriteScanDao favoriteScanDao();

    public static FavoriteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoriteDatabase.class, "favorite_scan_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
