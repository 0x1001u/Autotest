package com.android.autotest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.autotest.ui.theme.AutoTestTheme

class MainActivity : ComponentActivity() {
    val TAG = javaClass.simpleName
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
    fun Context.startApp(packageName: String, activityName: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                component = ComponentName(packageName, activityName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        } catch (e: Exception) {
            e.message?.let { Log.d(TAG,"$it") }
        }
    }
    @Composable
    fun MainView() {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Show the status of service")

            Button(onClick = {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }) {
               Text("Enable Service")
            }

            Button(onClick = {

            }) {
                Text("Stop Service")
            }

            Button(onClick = {
                    startApp("com.ss.android.ugc.aweme", "com.ss.android.ugc.aweme.main.MainActivity")
            }) {
                Text("Open Target")
            }
        }
    }
}
