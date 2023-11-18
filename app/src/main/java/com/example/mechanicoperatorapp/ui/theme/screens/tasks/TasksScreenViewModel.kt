package com.example.mechanicoperatorapp.ui.theme.screens.tasks

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


data class TasksScreenUIState(
    val tasks: List<String> = emptyList(),
    val filter: Int = 0,
)

data class SelectableUIState(
    val filter: Int = 0,
)

class TasksScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<TasksScreenUIState> = combine(
        selectableUIState,
        repository.getTaskModelById(1)
    ) { selectable, tasks ->


        TasksScreenUIState(
            tasks = listOf(tasks.templateName),
            filter = selectable.filter,
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksScreenUIState()
    )

}

class TasksScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksScreenViewModel(repository) as T
    }
}