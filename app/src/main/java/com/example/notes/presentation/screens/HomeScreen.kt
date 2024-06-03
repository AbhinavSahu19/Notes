package com.example.notes.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.R
import com.example.notes.database.NoteDataClass
import com.example.notes.presentation.AppViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun NotesHomeScreen(
    navigateToNoteEntry: () -> Unit,
    navigateToNoteEdit: (Int) -> Unit,
    navigateToNoteDisplay: (Int) -> Unit,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val homeUiState by homeViewModel.notesHomeUiState.collectAsState()

    Scaffold(
        topBar = {
             HomeTopBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToNoteEntry() },
                shape = MaterialTheme.shapes.medium,
                containerColor = colorResource(id = R.color.dark)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "add_button"
                )
            }
    }) {
        HomeBody(
            notesList = homeUiState.listOfNotes,
            onEditClick = navigateToNoteEdit,
            onDeleteClick = {
                coroutineScope.launch {
                    homeViewModel.deleteNote(it)
                }
            },
            onNoteClick = navigateToNoteDisplay,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = colorResource(id = R.color.mid)),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Box(
            ) {
                Text(
                    text = "Notes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.dark), titleContentColor = Color.Black
        ), modifier = Modifier.fillMaxWidth()
    )

}


@Composable
fun HomeBody(
    notesList: List<NoteDataClass>,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        if (notesList.isEmpty()) {
            run {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_icon),
                        contentDescription = "empty_icon",
                        modifier = Modifier.size(40.dp)
                    )

                    Text(
                        text = "No Notes Found",
                        fontSize = 18.sp
                    )
                }
            }
        } else {
            NotesHomeList(
                notesList = notesList,
                onItemClick = { onNoteClick(it.id) },
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
private fun NotesHomeList(
    notesList: List<NoteDataClass>,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onItemClick: (NoteDataClass) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = notesList, key = { it.id }) { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NoteUi(
                    notes = item,
                    onItemClick = onItemClick,
                    navigateToNoteEdit = onEditClick,
                    deleteNote = onDeleteClick,
                )
            }
        }
    }
}


@Composable
fun NoteUi(
    notes: NoteDataClass,
    onItemClick: (NoteDataClass) -> Unit,
    navigateToNoteEdit: (Int) -> Unit,
    deleteNote: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(5.dp)
            )
            .background(color = colorResource(id = R.color.light))
            .clickable { onItemClick(notes) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.light))
                .padding(vertical = 3.dp)
        ) {
            Text(
                text = notes.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, top = 4.dp, bottom = 5.dp),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = notes.ddt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, bottom = 5.dp),
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = notes.desc,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, bottom = 6.dp),
                fontSize = 16.sp,
                maxLines = 1,
            )
        }
        Box(
            modifier = Modifier.background(color = colorResource(id = R.color.light))
        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(id = R.drawable.three_dot_icon),
                    contentDescription = "option_icon"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(color = colorResource(id = R.color.light))
            ) {
                DropdownMenuItem(text = { Text(text = "Edit") },
                    onClick = { navigateToNoteEdit(notes.id) })
                DropdownMenuItem(text = { Text(text = "Delete") },
                    onClick = { deleteNote(notes.id) })

            }
        }

    }
}
