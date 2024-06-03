package com.example.notes.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.database.NotesRepository
import com.example.notes.presentation.NotesListUiState
import com.example.notes.presentation.navigation.NavigationDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

object HomeDestination: NavigationDestination {
    override val route = "note_home"
    override val titleRes = R.string.note_home_title
}

class HomeViewModel(
    private val notesRepository: NotesRepository
): ViewModel(){

    suspend fun deleteNote(noteId: Int) {
        notesRepository.deleteNote(noteId)
    }

    //fetching the list of note and mapping it to noteUiState
    val notesHomeUiState: StateFlow<NotesListUiState> =
        notesRepository.getAllNotesStream().map { NotesListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = NotesListUiState()
            )

}