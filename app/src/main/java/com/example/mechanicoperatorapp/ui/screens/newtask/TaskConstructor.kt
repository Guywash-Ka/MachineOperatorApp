package com.example.mechanicoperatorapp.ui.screens.newtask

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
    fields: List<FieldUIState>
) {
    var showChooser by remember { mutableStateOf(false) }
    var listToChooseFrom by remember {
        mutableStateOf(emptyList<String>())
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
                    start = pv.calculateStartPadding(LayoutDirection.Ltr),
                    end = pv.calculateEndPadding(LayoutDirection.Ltr)
                )
                .fillMaxWidth()
        ) {
            fields.forEach {

                OutlinedButton(onClick = {
                    showChooser = true
                    listToChooseFrom = it.options
                }) {
                    Text(it.fieldName)
                }

            }
        }
        
        if (showChooser) {
            Dialog(onDismissRequest = { showChooser = false }) {
                Card(
                    modifier = Modifier.padding(64.dp)
                ) {

                    listToChooseFrom.forEachIndexed() { index, item ->
                        Button(onClick = { /*TODO*/ }) {
                            Text(item)
                        }
                    }

                }
            }
        }

    }
}