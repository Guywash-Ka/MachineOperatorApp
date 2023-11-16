package com.example.mechanicoperatorapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.mechanicoperatorapp.ui.theme.MechanicOperatorAppTheme
import com.example.mechanicoperatorapp.worker.DownloadWorker
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Worker start
        val channel =
            NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val downloadWorkRequest: WorkRequest = PeriodicWorkRequest.Builder(DownloadWorker::class.java, 15.minutes.toJavaDuration())
            .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build())
            .build()

        WorkManager.getInstance(this).enqueue(downloadWorkRequest)

        // Worker end

        setContent {
            MechanicOperatorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MechanicOperatorAppTheme {
        Greeting("Android")
    }
}