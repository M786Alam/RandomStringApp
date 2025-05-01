package com.example.randomstringapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.randomstringapp.view.RandomStringScreen
import com.example.randomstringapp.viewmodel.RandomStringViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: RandomStringViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomStringScreen(viewModel)
        }
    }
}
