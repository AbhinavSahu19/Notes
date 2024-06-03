package com.example.notes.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notes.database.NoteDataClass
import com.example.notes.getDdt

data class NoteUiState(
    val id :Int = 0,
    val title: String = "Title",
    val ddt: String = getDdt(),
    val desc: String = "",
)

data class NotesListUiState(
    val  listOfNotes: List<NoteDataClass> = listOf()
)

