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
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

sealed class Screen(val route: String, @StringRes val name: Int) {
    object Messages : Screen("rides", R.string.messages_screen)
    object Tasks : Screen("main", R.string.tasks_screen)
    object Profile : Screen("profile", R.string.profile_screen)
    object AddTask : Screen("add-task", R.string.add_task_screen)
}

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


    @OptIn(ExperimentalMaterial3Api::class)
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

            val navController = rememberNavController()


            MechanicOperatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when {
                        mainState.showSplash -> {

                        }

                        !mainState.isLoggedIn -> {

                            Column() {
                                Text("LOGIN SCREEN")

                                if (mainState.nfcSerialNumber != null) {
                                    LaunchedEffect(null) {

                                        val worker = repo.getProfileByNfc(mainState.nfcSerialNumber!!)

                                        if (worker.id != -1) {
                                            mainStateFlow.value = MainActivityState(false, true, null, worker)
                                        }

                                    }
                                }
                            }

                        }

                        else -> {

                            // INSIDE APPLICATION




                            Scaffold(
                                bottomBar = {
                                    NavigationBar(
                                        containerColor = Color.White
                                    ) {
                                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                                        val currentDestination = navBackStackEntry?.destination

                                        // TODO: DIVIDE BY TYPES
                                        val screens = listOf(
                                            Screen.Messages,
                                            Screen.Tasks,
                                            Screen.Profile
                                        )

                                        screens.forEach { screen ->

                                            NavigationBarItem(
                                                label = {
                                                    Text(
                                                        text = stringResource(screen.name),
                                                        fontSize = 10.sp,
                                                    )
                                                },
                                                selected =
                                                currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                                onClick = {

//                                                    Log.e(TAG, "Click: $screen}")

                                                    navController.navigate(screen.route) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                icon = {

                                                    Icon(
                                                        Icons.Rounded.AccountBox,
                                                        contentDescription = null
                                                    )

//                                                    when (screen) {
//                                                        Screen.Main -> Image(
//                                                            painterResource(R.drawable.ic_main),
//                                                            contentDescription = null,
//                                                            modifier = Modifier.size(24.dp)
//                                                        )
//
//                                                        Screen.Profile -> Image(
//                                                            painterResource(R.drawable.ic_profile),
//                                                            contentDescription = null,
//                                                            modifier = Modifier.size(24.dp)
//                                                        )
//
//
//                                                        Screen.Rides -> Image(
//                                                            painterResource(R.drawable.ic_rides),
//                                                            contentDescription = null,
//                                                            modifier = Modifier.size(24.dp)
//                                                        )
//                                                    }
                                                },
                                                colors = NavigationBarItemDefaults.colors(
                                                    selectedIconColor = Color(0xFFFFA96B),
                                                    selectedTextColor = Color(0xFFFFA96B),
                                                    unselectedTextColor = Color(0xFFA8A8A8),
                                                    indicatorColor = Color.White
                                                ),
                                                interactionSource = NoRippleInteractionSource()
                                            )
                                        }
                                    }
                                }
                            ) { innerPadding ->

                                NavHost(
                                    navController,
                                    startDestination = Screen.Tasks.route,
                                    Modifier.padding(innerPadding)
                                ) {
                                    composable(Screen.Messages.route) { Text("MESSAGES SCREEN") }
                                    composable(Screen.Tasks.route) { Text("TASKS SCREEN") }
                                    composable(Screen.Profile.route) { Text("PROFILE SCREEN") }
                                    composable(Screen.AddTask.route) { Text("ADD TASK SCREEN") }
                                }

                            }
                        }
                    }
                }
            }

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
