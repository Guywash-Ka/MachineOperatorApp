package com.example.mechanicoperatorapp.ui.screens.workerslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mechanicoperatorapp.data.AppRepository



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkersListScreen(
    viewModel: WorkersListScreenViewModel = viewModel(
        factory = WorkersListScreenViewModelFactory(AppRepository.get())
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Работники",
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
                    start = pv.calculateStartPadding(LayoutDirection.Ltr),
                    end = pv.calculateEndPadding(LayoutDirection.Ltr)
                )
                .fillMaxWidth()
        ) {

            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.workers) {
                    WorkerListItem(it)
                }
            }

        }
    }
}

@Composable
fun WorkerListItem(
    name: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0x848A94BA)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(16.dp)
        )
    }
}
