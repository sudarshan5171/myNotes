package com.example.mynotes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mynotes.Database.NotesDatabase
import com.example.mynotes.Model.Notes
import com.example.mynotes.Repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application){

    val repository: NotesRepository

    init {
        val dao= NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun getNotes(): LiveData<List<Notes>> {
        return repository.getAllNotes()
    }

    fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }
}