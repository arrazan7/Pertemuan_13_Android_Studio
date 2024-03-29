package com.example.pertemuan_13_2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Notes)

    @Update
    fun update(note: Notes)

    @Delete
    fun delete(note: Notes)

    @get:Query("SELECT * FROM note_table ORDER BY id ASC")
    val allNotes: LiveData<List<Notes>>
}