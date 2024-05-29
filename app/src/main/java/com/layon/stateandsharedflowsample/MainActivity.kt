package com.layon.stateandsharedflowsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.layon.stateandsharedflowsample.ui.theme.StateAndSharedFlowSampleTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val viewModel: HomeScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateAndSharedFlowSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            val text = viewModel.liveData.observeAsState()
            Text(text = text.value.toString())
            Button(
                onClick = {
                    viewModel.triggerLiveData()
                }) {
                Text("LiveData")
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            val text = viewModel.stateFlow.collectAsState().value
            Text(text = text)
            Button(
                onClick = {
                    viewModel.triggerStateFlow()
                }) {
                Text("StateFlow")
            }
        }

        val coroutineScope = rememberCoroutineScope()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Hello World!")
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.triggerFlow().collect {

                        }
                    }
                }) {
                Text("Flow")
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            val text = viewModel.sharedFlow.collectAsState("Initial SharedFlow").value
            Text(text = text)
            Button(
                onClick = {
                    viewModel.triggerSharedFlow()
                }) {
                Text("SharedFlow")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StateAndSharedFlowSampleTheme {
        val previewViewModel = HomeScreenViewModel()
        HomeScreen(previewViewModel)
    }
}