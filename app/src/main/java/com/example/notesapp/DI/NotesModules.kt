package com.example.notesapp.DI

import android.content.Context
import androidx.room.Room
import com.example.notesapp.Database.NoteDao
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Database.NotesRepository
import com.example.notesapp.utilities.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NotesModules {

    @Provides
    @Singleton
    fun bindNoteDao(noteDatabase: NoteDatabase)=noteDatabase.getNoteDao()

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context):NoteDatabase = Room.databaseBuilder(
        context, NoteDatabase::class.java,
        DATABASE_NAME
    ).build()


}