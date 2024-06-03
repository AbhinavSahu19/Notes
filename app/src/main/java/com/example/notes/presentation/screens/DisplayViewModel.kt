package com.example.notes.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.database.NotesRepository
import com.example.notes.presentation.NoteUiState
import com.example.notes.presentation.navigation.NavigationDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

object DisplayDestination: NavigationDestination{
    override val route: String = "note_display"
    override val titleRes: Int = R.string.note_display_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

class DisplayViewModel(
    savedStateHandle: SavedStateHandle,
    notesRepository: NotesRepository
): ViewModel(){
    //note id passed through arguement
    val noteId : Int = checkNotNull(savedStateHandle[DisplayDestination.noteIdArg])

    //fetching note with id from database and mapping to note ui state
    val noteUiState: StateFlow<NoteUiState> =
        notesRepository.getWithIdStream(noteId)
            .filterNotNull()
            .map { NoteUiState(id = it.id, title = it.title, ddt = it.ddt, desc = it.desc) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000) ,
                initialValue = NoteUiState()
            )
}