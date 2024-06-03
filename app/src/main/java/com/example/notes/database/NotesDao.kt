package com.example.notes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteDataClass)

    @Query( "DELETE FROM noteTable WHERE id = :id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(note: NoteDataClass)

    @Query("SELECT * FROM noteTable ORDER BY ddt DESC")
    fun getAllNotes(): Flow<List<NoteDataClass>>

    @Query("SELECT * FROM noteTable WHERE id = :id")
    fun getWithId(id: Int) : Flow<NoteDataClass>
}