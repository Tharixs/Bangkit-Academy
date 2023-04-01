package com.example.notesapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.database.Note
import com.example.notesapp.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel(){

    private val mNoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}