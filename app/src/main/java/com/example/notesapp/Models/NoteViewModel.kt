package com.example.notesapp.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Database.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   NoteViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {
    val allNotes : LiveData<List<Note>> = notesRepository.allNotes

    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.update(note)
    }


    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(note)
    }
}