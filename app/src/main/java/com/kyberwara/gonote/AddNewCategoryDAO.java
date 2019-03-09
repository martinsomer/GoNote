package com.kyberwara.gonote;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AddNewCategoryDAO {

    @Insert
    public void addCategory(CategoriesEntity category);

    @Query("SELECT * FROM categories")
    public List<CategoriesEntity> getCategories();
}
