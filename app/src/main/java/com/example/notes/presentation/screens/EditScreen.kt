package com.example.notes.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.presentation.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    editViewModel: EditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        coroutineScope.launch {
            editViewModel.updateNote()
        }
        navigateBack()
    }
    Scaffold (
        topBar = {
            EntryTopBar(
                onSaveClick = {
                    coroutineScope.launch {
                        editViewModel.updateNote()
                        navigateBack()
                    }
                },
                navigateBack = navigateBack
            )
        }
    ){
        NoteEntryBody(noteDetailsUiState = editViewModel.noteUiState,
            onTitleChange = editViewModel::updateUiTitle,
            ontDescChange = editViewModel::updateUiDesc,
            modifier = Modifier
                .fillMaxSize()
                .padding(it))
    }
}