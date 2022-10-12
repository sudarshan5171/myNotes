package com.example.mynotes.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynotes.Model.Notes

//data access object
@Dao
interface NotesDao {
    @Query("Select * from Notes Order by Notes.date DESC")
    fun getNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("Delete from notes where id= :id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)
}