package com.example.notes.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notes.R
import com.example.notes.database.NoteDataClass
import com.example.notes.database.NotesRepository
import com.example.notes.presentation.NoteUiState
import com.example.notes.presentation.navigation.NavigationDestination

object EntryDestination : NavigationDestination {
    override val route = "note_entry"
    override val titleRes = R.string.note_entry_title
}


class EntryViewModel(
    private val notesRepository: NotesRepository
) : ViewModel(){
    //creating a ui state for note
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    //method for saving note
    suspend fun saveNote(){
        notesRepository.insertNote(noteUiState.uiStateToNote())
    }

    //to update title in note ui state
    fun updateUiTitle(newTitle: String){
       noteUiState = noteUiState.copy(title = newTitle)
    }

    //to update desc in note ui state
    fun updateUiDesc(newDesc: String){
        noteUiState = noteUiState.copy(desc = newDesc)
    }
}

//note ui state to noteDataClass for saving
fun NoteUiState.uiStateToNote(): NoteDataClass = NoteDataClass(
    id = id,
    title = title,
    ddt = ddt,
    desc = desc
)
