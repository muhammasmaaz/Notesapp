package com.example.notesapp.DI

import android.content.Context
import androidx.room.Room
import com.example.notesapp.Database.NoteDao
import com.example.notesapp.Database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @Singleton
    @Provides
    fun getDbname():String="RoomDb"

    @Singleton
    @Provides

    fun getroomDb(@ApplicationContext context: Context,name: String): NoteDatabase = Room.databaseBuilder(context,NoteDatabase::class.java,name).build()

    @Singleton
    @Provides

    fun getNotesDao(db:NoteDatabase):NoteDao=db.getNoteDao()



}