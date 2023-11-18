package com.example.mechanicoperatorapp.ui.screens.newtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mechanicoperatorapp.data.AppRepository
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class AddTaskScreenUIState(
    val workers: List<WorkManEntity> = emptyList(),
)

data class SelectableUIState(
    val filter: Int = 0,
)

class AddTaskScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<AddTaskScreenUIState> = combine(
        selectableUIState,
        repository.getWorkMans()
    ) { selectable, workers ->

        AddTaskScreenUIState(
            workers = workers,
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddTaskScreenUIState()
    )

}

class AddTaskScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskScreenViewModel(repository) as T
    }
}