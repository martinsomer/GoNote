package com.kyberwara.gonote;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AddNewCategoryDAO {

    @Insert
    public void addCategory(CategoriesEntity category);

    @Query("SELECT * FROM categories")
    public List<CategoriesEntity> getCategories();

    @Query("SELECT * FROM categories WHERE ID=:ID")
    public CategoriesEntity getCategory(int ID);

    @Delete
    public void deleteCategory(CategoriesEntity category);

    @Update
    public void updateCategory(CategoriesEntity category);
}
