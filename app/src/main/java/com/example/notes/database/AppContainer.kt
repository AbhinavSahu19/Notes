package com.example.notes.database

import android.content.Context


//For dependency injection
interface AppContainer{
    val notesRepository : NotesRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).notesDao())
    }
}