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
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
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
import com.example.mechanicoperatorapp.ui.screens.LoginScreen
import com.example.mechanicoperatorapp.ui.theme.MechanicOperatorAppTheme
import com.example.mechanicoperatorapp.ui.screens.agronomistmessages.AgronomistMessagesScreen
import com.example.mechanicoperatorapp.ui.screens.agronomprofile.AgronomistProfileScreen
import com.example.mechanicoperatorapp.ui.screens.newtask.AddTaskScreen
import com.example.mechanicoperatorapp.ui.screens.tasks.TasksScreen
import com.example.mechanicoperatorapp.ui.screens.workerslist.WorkersListScreen
import com.example.mechanicoperatorapp.worker.DownloadWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

sealed class Screen(val route: String, @StringRes val name: Int) {
    object WorkerMessages : Screen("worker-messages", R.string.messages_screen)
    object Tasks : Screen("tasks", R.string.tasks_screen)
    object WorkerProfile : Screen("worker-profile", R.string.profile_screen)
    object AddTask : Screen("add-task", R.string.add_task_screen)
    object AgronomistMessages : Screen("agronomist-messages", R.string.messages_screen)
    object AgronomistProfile : Screen("agronomist-profile", R.string.profile_screen)
    object WorkersList : Screen("workers-list", R.string.workers_list_screen)
}

private data class MainActivityState(
    val showSplash: Boolean = false,
    val isLoggedIn: Boolean = false,
    val nfcSerialNumber: String? = null,
    val worker: RoleAndId? = null,
)

//private data class MainActivityState(
//    val showSplash: Boolean = false,
//    val isLoggedIn: Boolean = true,
//    val nfcSerialNumber: String? = null,
//    val worker: RoleAndId? = RoleAndId(1, "worker"),
//)


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
//            loadData(repo)
//            loadFields(repo)
//            loadTemplates(repo)
//            loadTasks(repo)
//            loadAgronom(repo)
//            loadWorker(repo)

        setContent {

            val mainState by mainStateFlow.collectAsStateWithLifecycle()

            val navController = rememberNavController()
            val scope = rememberCoroutineScope()

            var loginShowTryAgain by remember { mutableStateOf(false) }

            MechanicOperatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when {
                        mainState.showSplash -> {

                        }

                        !mainState.isLoggedIn -> {

                            LoginScreen(
                                showTryAgain = loginShowTryAgain,
                                onSendPassword = { password ->
                                    scope.launch {
                                        val worker = repo.getProfileByPassword(password)
                                        if (worker.id != -1) {
                                            mainStateFlow.value = MainActivityState(false, true, null, worker)
                                        } else {
                                            loginShowTryAgain = true
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(null) {
                                repo.sync()
                            }

                            if (mainState.nfcSerialNumber != null) {
                                LaunchedEffect(mainState) {

                                    Log.e("MainActivity", "NFC = ${mainState.nfcSerialNumber}")
                                    val worker = repo.getProfileByNfc(mainState.nfcSerialNumber!!)

                                    if (worker.id != -1) {
                                        mainStateFlow.value = MainActivityState(false, true, null, worker)
                                    } else {
                                        loginShowTryAgain = true
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
                                        val screens = when (mainState.worker!!.role) {
                                            "worker" -> listOf(
                                                Screen.WorkerMessages,
                                                Screen.Tasks,
                                                Screen.WorkerProfile
                                            )
                                            "agronom" -> listOf(
                                                Screen.WorkersList,
                                                Screen.AddTask,
                                                Screen.AgronomistMessages,
                                                Screen.AgronomistProfile,
                                            )
                                            else -> emptyList()
                                        }


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
                                    startDestination = if (mainState.worker!!.role == "worker") Screen.Tasks.route else Screen.AddTask.route,
                                    Modifier.padding(innerPadding)
                                ) {
                                    composable(Screen.WorkerMessages.route) {
                                        Scaffold(
                                            topBar = {
                                                TopAppBar(
                                                    title = {
                                                        Text(
                                                            text = "Сообщения",
                                                            fontSize = 24.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                )
                                            }
                                        ) { pv ->
                                            Column(
                                                modifier = Modifier
                                                    .padding(
                                                        bottom = pv.calculateBottomPadding(),
                                                        top = pv.calculateTopPadding(),
                                                        start = pv.calculateStartPadding(
                                                            LayoutDirection.Ltr),
                                                        end = pv.calculateEndPadding(LayoutDirection.Ltr)
                                                    )
                                                    .fillMaxWidth()
                                            ) {

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                                                ) {
                                                    Icon(Icons.Rounded.AccountCircle, contentDescription = null, modifier = Modifier.size(48.dp).padding(horizontal = 8.dp))

                                                    Column {
                                                        Text("SVTV NEWS")
                                                        Text("Independent libertarian mass media", fontSize = 8.sp)
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    composable(Screen.Tasks.route) { TasksScreen() }
                                    composable(Screen.WorkerProfile.route) {
                                        Scaffold(
                                            topBar = {
                                                TopAppBar(
                                                    title = {
                                                        Text(
                                                            text = "Профиль",
                                                            fontSize = 24.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                )
                                            }
                                        ) { pv ->
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .padding(
                                                        bottom = pv.calculateBottomPadding(),
                                                        top = pv.calculateTopPadding(),
                                                        start = pv.calculateStartPadding(LayoutDirection.Ltr),
                                                        end = pv.calculateEndPadding(LayoutDirection.Ltr)
                                                    )
                                                    .fillMaxWidth()
                                            ) {
                                                Icon(Icons.Rounded.AccountCircle, null, modifier = Modifier.size(128.dp).padding(32.dp))
                                                Text("Александр Пушной")
                                            }
                                        }
                                    }
                                    composable(Screen.AddTask.route) { AddTaskScreen() }
                                    composable(Screen.AgronomistMessages.route) { AgronomistMessagesScreen() }
                                    composable(Screen.AgronomistProfile.route) { AgronomistProfileScreen() }
                                    composable(Screen.WorkersList.route) { WorkersListScreen() }
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
fun loadData(repo: AppRepository) {
    GlobalScope.launch {

        repo.addOperation(1, "Посев")
        repo.addOperation(2, "Полив")
        repo.addOperation(3, "Удобрение")
        repo.addOperation(4, "Обработка почвы")
        repo.addOperation(5, "Уборка урожая")

// Add sample entries for FarmFieldEntity
        repo.addFarmField(1, "Поле 1")
        repo.addFarmField(2, "Поле 2")
        repo.addFarmField(3, "Поле 3")
        repo.addFarmField(4, "Поле 4")
        repo.addFarmField(5, "Поле 5")

// Add sample entries for WorkManEntity
        repo.addWorkMan(1, "Иван")
        repo.addWorkMan(2, "Михаил")
        repo.addWorkMan(3, "Анатолий")
        repo.addWorkMan(4, "Ольга")
        repo.addWorkMan(5, "Евгения")

// Add sample entries for TransportEntity
        repo.addTransport(1, "Трактор")
        repo.addTransport(2, "Комбайн")
        repo.addTransport(3, "Грузовик")
        repo.addTransport(4, "Прицеп")
        repo.addTransport(5, "Автомобиль")

// Add sample entries for AgregatEntity
        repo.addAgregat(1, "Плуг")
        repo.addAgregat(2, "Борона")
        repo.addAgregat(3, "Сеялка")
        repo.addAgregat(4, "Культиватор")
        repo.addAgregat(5, "Аэратор")

// Add sample entries for DepthEntity
        repo.addDepth(1, "10 см")
        repo.addDepth(2, "12 см")
        repo.addDepth(3, "15 см")
        repo.addDepth(4, "18 см")
        repo.addDepth(5, "20 см")

// Add sample entries for SpeedEntity
        repo.addSpeed(1, "5 км/ч")
        repo.addSpeed(2, "7 км/ч")
        repo.addSpeed(3, "10 км/ч")
        repo.addSpeed(4, "12 км/ч")
        repo.addSpeed(5, "15 км/ч")

// Add sample entries for WaterEntity
        repo.addWater(1, "Нормальное увлажнение")
        repo.addWater(2, "Легкое увлажнение")
        repo.addWater(3, "Избыточное увлажнение")
        repo.addWater(4, "Недостаточное увлажнение")
        repo.addWater(5, "Среднее увлажнение")
    }
}

fun loadFields(repo: AppRepository) {
    GlobalScope.launch {
        repo.addFieldWithName(1, "Операция")
        repo.addFieldWithName(2, "Поле")
        repo.addFieldWithName(3, "Исполнитель")
        repo.addFieldWithName(4, "Техника")
        repo.addFieldWithName(5, "Агрегат")
        repo.addFieldWithName(6, "Глубина")
        repo.addFieldWithName(7, "Скорость")
        repo.addFieldWithName(8, "Расход раствора")
    }
}

fun loadTemplates(repo: AppRepository) {
    GlobalScope.launch {
        repo.addTemplateWithTitleAndFields(1, "Полив", listOf(1, 3, 4))
        repo.addTemplateWithTitleAndFields(2, "Посев", listOf(1, 2, 7))
        repo.addTemplateWithTitleAndFields(3, "Починка", listOf(3, 4))
    }
}

fun loadTasks(repo: AppRepository) {
    GlobalScope.launch {
        repo.addTask(1, 2, 1, 1, listOf(1, 3, 2))
        repo.addTask(2, 1, 2, 3, listOf(2, 2))
        repo.addTask(3, 2, 1, 3, listOf(3, 3))
    }
}

fun loadAgronom(repo: AppRepository) {
    GlobalScope.launch {
        repo.addAgronom(1, "Иванов Петр", "1234567", "11:11:11:11", 2000f)
        repo.addAgronom(2, "Белкин Никита", "qwerty", "12:11:11:11", 2000f)
        repo.addAgronom(3, "Лазарев Сергей", "admin", "13:11:11:11", 2000f)
        repo.addAgronom(4, "Ракитин Егор", "login", "14:11:11:11", 2000f)
        repo.addAgronom(5, "Мирон Федоров", "password", "15:11:11:11", 2000f)
    }
}

fun loadWorker(repo: AppRepository) {
    GlobalScope.launch {
        repo.addWorker(1, "Смирнов Александр" ,"0987", "16:11:11:11", 1000f)
        repo.addWorker(2, "Курыкин Владимир" ,"0988", "17:11:11:11", 1000f)
        repo.addWorker(3, "Петросян Оганес" ,"0989", "18:11:11:11", 1000f)
        repo.addWorker(4, "Лежнина Елена" ,"0997", "19:11:11:11", 1000f)
        repo.addWorker(5, "Кривошеин Александр" ,"0998", "20:11:11:11", 1000f)
    }
}
