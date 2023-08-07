package com.example.notesapp.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.Models.Note


@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao():NoteDao
}