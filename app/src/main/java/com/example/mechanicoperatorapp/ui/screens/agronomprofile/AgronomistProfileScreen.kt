package com.example.mechanicoperatorapp.ui.screens.agronomprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mechanicoperatorapp.data.AppRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgronomistProfileScreen(
    viewModel: AgronomistProfileScreenViewModel = viewModel(
        factory = AgronomistProfileScreenViewModelFactory(AppRepository.get())
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            Text(uiState.name)
        }
    }
}