package com.example.notes

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.presentation.navigation.NoteNavHost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@SuppressLint("WeekBasedYear")
fun getDdt(): String{
    val sdf = SimpleDateFormat("EEEE  dd/MM/YYYY  HH:mm a", Locale.ENGLISH)
    sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    return sdf.format(Date())
}

@Composable
fun NotesApp(navController: NavHostController = rememberNavController()) {
    NoteNavHost(navController = navController)
}