package com.example.notes.database

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(
    private val notesDao: NotesDao
) : NotesRepository {

    override fun getAllNotesStream(): Flow<List<NoteDataClass>> = notesDao.getAllNotes()
    override fun getWithIdStream(id: Int): Flow<NoteDataClass> = notesDao.getWithId(id)

    override suspend fun insertNote(note: NoteDataClass) = notesDao.insert(note)
    override suspend fun deleteNote(id: Int) = notesDao.delete(id)
    override suspend fun updateNote(note: NoteDataClass) = notesDao.update(note)
}