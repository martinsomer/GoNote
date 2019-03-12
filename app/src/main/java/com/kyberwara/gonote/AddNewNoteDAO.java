package com.kyberwara.gonote;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AddNewNoteDAO {

    @Insert
    public void addNote(NotesEntity note);

    @Query("SELECT * FROM notes WHERE categoryID=:id")
    public List<NotesEntity> getNotes(int id);
}
