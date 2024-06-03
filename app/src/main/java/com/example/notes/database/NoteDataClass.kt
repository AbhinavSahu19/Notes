package com.example.notes.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "noteTable")
data class NoteDataClass(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val ddt : String,
    val desc : String
)
