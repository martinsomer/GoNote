package com.kyberwara.gonote;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface AddNewNoteDAO {

    @Insert
    public void addNote(NotesEntity note);
}
