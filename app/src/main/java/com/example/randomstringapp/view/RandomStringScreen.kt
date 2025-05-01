package com.example.randomstringapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomstringapp.viewmodel.RandomStringViewModel

@Composable
fun RandomStringScreen(viewModel: RandomStringViewModel) {
    val strings by viewModel.strings.collectAsState()
    val error by viewModel.error.collectAsState()

    var input by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Length") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                input.toIntOrNull()?.let { viewModel.generateString(it) }
            }) {
                Text("Generate")
            }

            Button(onClick = viewModel::clearAll) {
                Text("Clear All")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            itemsIndexed(strings) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Value: ${item.value}")
                        Text("Length: ${item.length}")
                        Text("Created: ${item.created}")

                        TextButton(
                            onClick = { viewModel.deleteAt(index) },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        error?.let {
            Snackbar(
                action = {
                    TextButton(onClick = viewModel::clearError) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}
