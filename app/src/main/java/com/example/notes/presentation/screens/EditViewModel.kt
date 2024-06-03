package com.example.notes.presentation.screens

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.R
import com.example.notes.database.NoteDataClass
import com.example.notes.database.NotesRepository
import com.example.notes.getDdt
import com.example.notes.presentation.NoteUiState
import com.example.notes.presentation.navigation.NavigationDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//object of edit screen destinaiton for navigation
object EditDestination: NavigationDestination{
    override val route = "note_edit"
    override val titleRes = R.string.note_edit_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
): ViewModel(){
    //getting noteId from arguement through savedStateHandle
    val noteId: Int = checkNotNull(savedStateHandle[EditDestination.noteIdArg])

    //creating noteUiState for edit screen. Since we have to edit it so creating a mutableState
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    //fetching the note data from the database and conteting to note ui state
    init{
        viewModelScope.launch {
            noteUiState = notesRepository.getWithIdStream(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState()
        }
    }

    //save the updated note
    suspend fun updateNote(){
        viewModelScope.launch {
            notesRepository.updateNote(noteUiState.uiStateToNote())
        }
    }

    //this method will be called from the screen to change title of ui state
    fun updateUiTitle(newTitle: String) {
        noteUiState = noteUiState.copy(title = newTitle)
    }

    //this method will be called form the screen to change description of ui state
    fun updateUiDesc(newDesc: String){
        noteUiState = noteUiState.copy(desc = newDesc)
    }
}

//note to note ui state when fetching from db
fun NoteDataClass.toNoteUiState(): NoteUiState = NoteUiState(
        id = id,
        title = title,
        ddt = getDdt(),
        desc = desc
    )
