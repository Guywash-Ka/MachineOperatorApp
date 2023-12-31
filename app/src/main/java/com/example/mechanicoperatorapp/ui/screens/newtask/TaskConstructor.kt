package com.example.mechanicoperatorapp.ui.screens.newtask

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.window.Dialog
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskConstructor(
    template: TemplatesModel?,
    fields: List<FieldUIState>,
    selectOption: (Int, Int) -> Unit,
    onSave: () -> Unit,
) {
    var showChooser by remember { mutableStateOf(false) }
    var listToChooseFrom by remember {
        mutableStateOf(emptyList<Pair<String, Int>>())
    }
    var optionIndex by remember {
        mutableStateOf<Int?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = template?.title ?: "Новый шаблон",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            )
        },
    ) { pv ->
        Column(
            modifier = Modifier
                .padding(
                    bottom = pv.calculateBottomPadding(),
                    top = pv.calculateTopPadding(),
                    start = pv.calculateStartPadding(LayoutDirection.Ltr) + 8.dp,
                    end = pv.calculateEndPadding(LayoutDirection.Ltr) + 8.dp
                )
                .fillMaxWidth()
        ) {
            fields.forEachIndexed { index, field ->

                OutlinedButton(onClick = {
                    showChooser = true
                    listToChooseFrom = field.options
                    optionIndex = index
                },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    if (field.chosenOption != null) {
                        Text(field.chosenOption)
                    } else {
                        Text(field.fieldName)
                    }
                }

            }

            Button(onClick = {
                onSave()
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
        
        if (showChooser) {
            Dialog(onDismissRequest = { showChooser = false }) {
                Card(
                    modifier = Modifier.padding(64.dp)
                ) {

                    listToChooseFrom.forEachIndexed() { index, item ->
                        OutlinedButton(onClick = {

                            Log.e("TaskConstructor", "HERE: ${optionIndex} ${item.second}")

                            selectOption(optionIndex!!, item.second)
                            showChooser = false
                        },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text(item.first)
                        }
                    }

                }
            }
        }

    }
}