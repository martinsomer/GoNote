package com.kyberwara.gonote;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {CategoriesEntity.class, NotesEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract CategoriesDAO CategoriesDAO();
    public abstract NotesDAO NotesDAO();
}
