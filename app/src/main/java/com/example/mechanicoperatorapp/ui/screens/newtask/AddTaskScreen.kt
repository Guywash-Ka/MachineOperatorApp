package com.example.mechanicoperatorapp.ui.screens.newtask

import android.graphics.Outline
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mechanicoperatorapp.data.AppRepository
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: AddTaskScreenViewModel = viewModel(
        factory = AddTaskScreenViewModelFactory(AppRepository.get())
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showConstructor by remember { mutableStateOf(false) }
    var selectedTemplate by remember { mutableStateOf<TemplatesModel?>(null) }

    if (showConstructor) {
        TaskConstructor(
            template = selectedTemplate,
            fields = uiState.fieldsUIState,
            selectOption = { index, id ->
                viewModel.selectOption(index, id)
            },
            onSave = {
                viewModel.onSave(selectedTemplate!!.id)
                showConstructor = false
                selectedTemplate = null
            },
        )
    } else {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Задачи",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showBottomSheet = true }) {
                    Icon(Icons.Outlined.Add, null, modifier = Modifier.size(32.dp))
                }
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

                Text(
                    text = "Статистика по задачам",
                    fontWeight = FontWeight.Bold,
                )

                Text("Всего: 276")

            }


            if (showBottomSheet) {
                ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {

                    Column {

                        uiState.templates.forEach { template ->

                            OutlinedButton(
                                onClick = {
                                    selectedTemplate = template
//                                    viewModel.setFields(template.taskFields)
                                    viewModel.setFields(listOf(1, 2, 3))
                                    showBottomSheet = false
                                    showConstructor = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(template.title)
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                showBottomSheet = false
                                showConstructor = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Создать новый шаблон")
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                        )
                    }
                }
            }
        }
    }

}