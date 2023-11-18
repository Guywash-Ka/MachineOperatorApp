package com.example.mechanicoperatorapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.mechanicoperatorapp.data.AppRepository
import com.example.mechanicoperatorapp.data.dataClasses.RoleAndId
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import com.example.mechanicoperatorapp.ui.theme.MechanicOperatorAppTheme
import com.example.mechanicoperatorapp.worker.DownloadWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private data class Worker(
    val id: Int,
    val role: Int,
)

private data class MainActivityState(
    val showSplash: Boolean = false,
    val isLoggedIn: Boolean = false,
    val nfcSerialNumber: String? = null,
    val worker: RoleAndId? = null,
)

class MainActivity : ComponentActivity() {

    private lateinit var nfcPendingIntent: PendingIntent

    private val mainStateFlow: MutableStateFlow<MainActivityState> = MutableStateFlow(
        MainActivityState()
    )


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

        nfcPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            },
            PendingIntent.FLAG_MUTABLE
        )

        val repo = AppRepository.get()

        setContent {
            val mainState by mainStateFlow.collectAsStateWithLifecycle()
            when {
                mainState.showSplash -> {
                }
                !mainState.isLoggedIn -> {
                    Column() {
                        Text("LOGIN SCREEN")
                        if (mainState.nfcSerialNumber != null) {
                            Text("NFC = ${mainState.nfcSerialNumber}")
                            LaunchedEffect(null) {
                                delay(1000)
                                mainStateFlow.value = MainActivityState(false, true, null, Worker(0, 0))
                            }
                            // SEND NFC FROM HERE

                            // on success clear nfc serial
                        }
                    }
                }
                else -> {
                    Text("INSIDE APPLICATION")
                    // mainState.worker is not null here
                }
            }

//            MechanicOperatorAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        if (tag != null && !mainStateFlow.value.isLoggedIn) {

            val id = tag.id
                .toList()
                .joinToString(":") {
                    Integer
                        .toHexString(it.toInt())
                        .takeLast(2)
                        .let { if (it.length == 1) "0$it" else it }
                        .uppercase()
                }

            mainStateFlow.update {
                it.copy(
                    nfcSerialNumber = id
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
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