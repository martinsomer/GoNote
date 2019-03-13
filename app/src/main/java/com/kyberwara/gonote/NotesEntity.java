package com.kyberwara.gonote;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Notes", indices = {@Index("categoryID")}, foreignKeys = @ForeignKey(entity = CategoriesEntity.class, parentColumns = "ID", childColumns = "categoryID", onDelete = CASCADE, onUpdate = CASCADE))
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    private int categoryID;

    private String title;

    private String content;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
