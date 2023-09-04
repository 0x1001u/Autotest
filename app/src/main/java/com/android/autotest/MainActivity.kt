package com.android.autotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.autotest.ui.theme.AutoTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   MainView()
                }
            }
        }
    }
    @Composable
    fun MainView() {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Show the status of service")

            Button(onClick = {
                startActivity()

            }) {
               Text("Enable Service")
            }

            Button(onClick = {

            }) {
                Text("Stop Service")
            }

            Button(onClick = {

            }) {
                Text("Open Target")
            }
        }
    }
}

// function to check the service status
// open the service