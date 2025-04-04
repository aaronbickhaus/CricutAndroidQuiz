package com.cricut.androidquiz

import android.annotation.SuppressLint
import com.cricut.androidquiz.ui.screen.QuizScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.cricut.androidquiz.ui.viewmodel.MainViewModel
import com.cricut.androidquiz.ui.theme.CricutAndroidQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        enableEdgeToEdge()
        setContent {
            CricutAndroidQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(viewModel)
                }
            }
        }
    }
}