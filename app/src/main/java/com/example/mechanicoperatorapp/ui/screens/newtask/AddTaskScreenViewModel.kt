package com.example.mechanicoperatorapp.ui.screens.newtask

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
    val options: List<String>,
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
            fieldsUIState = selectable.fieldsIds.map { (id, _) ->
                FieldUIState(
                    fieldName = repository.getFieldById(id).first().name,
                    options = repository.getFieldOptionsById(id).first(),
                    chosenOption = repository.getFieldOptionsById(id).first()
            }
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddTaskScreenUIState()
    )

    fun selectOption()

    fun setFields(fieldsIds: List<Int>) {
        selectableUIState.update {
            it.copy(
                fieldsIds = fieldsIds
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