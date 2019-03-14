package com.kyberwara.gonote;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AddNewNoteDAO {

    @Insert
    public void addNote(NotesEntity note);

    @Query("SELECT * FROM notes WHERE categoryID=:ID")
    public List<NotesEntity> getNotes(int ID);

    @Query("SELECT * FROM notes WHERE ID=:ID")
    public NotesEntity getNote(int ID);

    @Query("SELECT * FROM notes")
    public List<NotesEntity> getAllNotes();

    @Query("SELECT COUNT(ID) FROM notes")
    public int getNumberOfNotes();

    @Query("SELECT COUNT(ID) FROM notes WHERE categoryID=:ID")
    public int getNumberOfNotesInCategory(int ID);

    @Delete
    public void deleteNote(NotesEntity note);

    @Update
    public void updateNote(NotesEntity note);
}
