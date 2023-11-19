package com.example.mechanicoperatorapp.ui.screens.newtask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mechanicoperatorapp.data.AppRepository
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesModel
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddTaskScreenUIState(
    val workers: List<WorkManEntity> = emptyList(),
    val templates: List<TemplatesModel> = emptyList(),

    val fieldsUIState: List<FieldUIState> = emptyList()
)

data class SelectableUIState(
    val filter: Int = 0,
    // ID to selected ID
    val fieldsIds: List<Pair<Int, Int>> = emptyList()
)

data class FieldUIState(
    val fieldName: String,
    val options: List<Pair<String, Int>>,
    val chosenOption: String? = null
)

class AddTaskScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<AddTaskScreenUIState> = combine(
        selectableUIState,
        repository.getWorkMans(),
        repository.getTemplates(),
    ) { selectable, workers, templates ->

        AddTaskScreenUIState(
            workers = workers,
            templates = templates,
            fieldsUIState = selectable.fieldsIds.map { (id, selectedId) ->
                FieldUIState(
                    fieldName = repository.getFieldById(id).first().name,
                    options = repository.getFieldOptionsById(id).first(),
                    chosenOption = repository
                        .getFieldOptionsById(id).first()
                        .find { it.second == selectedId }?.first,
                )
            }
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddTaskScreenUIState()
    )

    fun selectOption(index: Int, id: Int) {
        selectableUIState.update {

            val list = it.fieldsIds.toMutableList()

            Log.e("AddTask", "$list ($id)")

            list[index] = list[index].copy(
                second = id
            )

            Log.e("AddTask", "$list")

            it.copy(
                fieldsIds = list
            )
        }
    }

    fun onSave(templateId: Int) {

        viewModelScope.launch {

            Log.e("AddTask", "SENDING TASK!!!")

            repository.addTask(
                id = 1337,
                agronomId = repository.getId().first(),
                workerId = 1,
                templateId = templateId,
                tasks = selectableUIState.value.fieldsIds.map { it.second }
            )

        }

    }

    fun setFields(fieldsIds: List<Int>) {
        selectableUIState.update {
            it.copy(
                fieldsIds = fieldsIds.map { it to -1 }
            )
        }
    }

}

class AddTaskScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskScreenViewModel(repository) as T
    }
}