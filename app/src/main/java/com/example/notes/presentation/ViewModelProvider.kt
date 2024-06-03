package com.example.notes.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notes.NotesApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import com.example.notes.presentation.screens.DisplayViewModel
import com.example.notes.presentation.screens.EditViewModel
import com.example.notes.presentation.screens.EntryViewModel
import com.example.notes.presentation.screens.HomeViewModel

object AppViewModelProvider {
    val Factory : ViewModelProvider.Factory = viewModelFactory {
        initializer {
            EntryViewModel(notesApplication().container.notesRepository)
        }
        initializer {
            HomeViewModel(
                notesApplication().container.notesRepository)
        }
        initializer {
            EditViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository)
        }
        initializer {
            DisplayViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository)
        }
    }
}

fun CreationExtras.notesApplication(): NotesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)
