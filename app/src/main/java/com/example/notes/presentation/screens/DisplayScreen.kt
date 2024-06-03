package com.example.notes.presentation.screens

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.checkScrollableContainerConstraints
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.presentation.AppViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.R
import com.example.notes.presentation.NoteUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayScreen(
    navigateToEdit : (Int) -> Unit,
    navigateBack: () -> Unit,
    displayViewModel: DisplayViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val noteId: Int = displayViewModel.noteId
    val noteUiState by displayViewModel.noteUiState.collectAsState()
    Scaffold (
        topBar = {
            DisplayTopBar(navigateToEdit = { navigateToEdit(noteId) },
                navigateBack = navigateBack)
        },
        content = {
            DisplayBody(noteUiState = noteUiState,
                modifier = Modifier.padding(it)
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTopBar(
    navigateToEdit: () -> Unit,
    navigateBack: () -> Unit
){
    TopAppBar(
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Icon(painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "back_arrow",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { navigateBack() })
                Text(text = "Edit",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable { navigateToEdit() })
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.dark),
            titleContentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth())
}

@Composable
fun DisplayBody(
    noteUiState: NoteUiState,
    modifier: Modifier
){
    Column (
        modifier = modifier.background(color = colorResource(id = R.color.light))
    ){
        Text(text = noteUiState.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = noteUiState.ddt,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 15.sp
        )
        Divider(color = colorResource(id = R.color.dark),
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp)
        Text(
            text = noteUiState.desc,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            fontSize = 18.sp,
        )
    }
}

@Composable
@Preview
fun DisplayBodyPreview(){
    DisplayBody(noteUiState = NoteUiState(
        id = 0,
        title = "Name",
        ddt = "asfd",
        desc = "String"
    ),
        modifier = Modifier)
}