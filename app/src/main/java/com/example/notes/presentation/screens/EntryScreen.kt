package com.example.notes.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.R
import com.example.notes.presentation.AppViewModelProvider
import com.example.notes.presentation.NoteUiState
import kotlinx.coroutines.launch

@Composable
fun EntryScreen(
    navigateBack: () -> Unit,
    entryViewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        coroutineScope.launch {
            entryViewModel.saveNote()
        }
        navigateBack()
    }
    Scaffold(
        topBar = {
            EntryTopBar(
                onSaveClick = {
                    coroutineScope.launch {
                        entryViewModel.saveNote()
                    }
                    navigateBack()
                },
                navigateBack = navigateBack
            )
        }
    ) {
        NoteEntryBody(
            noteDetailsUiState = entryViewModel.noteUiState,
            onTitleChange = entryViewModel::updateUiTitle,
            ontDescChange = entryViewModel::updateUiDesc,
            modifier = Modifier.padding(it)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTopBar(
    onSaveClick: () -> Unit,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "back_arrow",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { navigateBack() })
                Text(text = "Save",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable { onSaveClick() },
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.dark),
            titleContentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteEntryBody(
    noteDetailsUiState: NoteUiState,
    onTitleChange: (String) -> Unit,
    ontDescChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light))
    ) {
        BasicTextField(
            value = noteDetailsUiState.title,
            onValueChange = {
                onTitleChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 6.dp)
                .focusRequester(focusRequester)
                .background(color = colorResource(id = R.color.light)),
            cursorBrush = SolidColor(Color.Black),
            textStyle = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Divider(
            color = colorResource(id = R.color.mid),
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp
        )
        Text(
            text = noteDetailsUiState.ddt,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .background(color = colorResource(id = R.color.light)),
            color = Color.Gray
        )
        Divider(
            color = colorResource(id = R.color.dark),
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        BasicTextField(
            value = noteDetailsUiState.desc,
            onValueChange = { ontDescChange(it) },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .padding(10.dp, 6.dp, 10.dp, if (WindowInsets.isImeVisible) 340.dp else 6.dp)
                .background(color = colorResource(id = R.color.light)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true
                ),
            textStyle = TextStyle(
                fontSize = 18.sp,
            )
        )
    }
}
