package com.example.notes.database

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotesStream() : Flow<List<NoteDataClass>>
    fun getWithIdStream(id : Int): Flow<NoteDataClass>

    suspend fun insertNote(note: NoteDataClass)
    suspend fun deleteNote(id: Int)
    suspend fun updateNote(note: NoteDataClass)
}