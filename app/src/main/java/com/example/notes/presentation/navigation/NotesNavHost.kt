package com.example.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.presentation.screens.DisplayDestination
import com.example.notes.presentation.screens.DisplayScreen
import com.example.notes.presentation.screens.EditDestination
import com.example.notes.presentation.screens.EditScreen
import com.example.notes.presentation.screens.EntryDestination
import com.example.notes.presentation.screens.EntryScreen
import com.example.notes.presentation.screens.HomeDestination
import com.example.notes.presentation.screens.NotesHomeScreen


@Composable
fun NoteNavHost(
    navController: NavHostController
){
    NavHost(navController = navController,
        startDestination = HomeDestination.route) {

        composable(route = HomeDestination.route){
            NotesHomeScreen(
                navigateToNoteEntry = { navController.navigate(EntryDestination.route)},
                navigateToNoteEdit = { navController.navigate("${EditDestination.route}/$it")},
                navigateToNoteDisplay = { navController.navigate("${DisplayDestination.route}/$it")}
            )
        }
        composable(route = EntryDestination.route){
            EntryScreen(navigateBack =  {navController.popBackStack()})
        }
        composable(route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.noteIdArg){type = NavType.IntType})){
            EditScreen(
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(route = DisplayDestination.routeWithArgs,
            arguments = listOf(navArgument(DisplayDestination.noteIdArg){type = NavType.IntType})){
            DisplayScreen(
                navigateToEdit = {navController.navigate("${EditDestination.route}/$it")},
                navigateBack = {navController.popBackStack()}
            )
        }
    }
}