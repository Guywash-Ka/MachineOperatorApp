package com.example.mechanicoperatorapp.ui.theme.screens.workerslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mechanicoperatorapp.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class WorkersListScreenUIState(
    val workers: List<String> = emptyList(),
    val filter: Int = 0,
)

data class SelectableUIState(
    val filter: Int = 0,
)

class WorkersListScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<WorkersListScreenUIState> = combine(
        selectableUIState,
        repository.getWorkMans()
    ) { selectable, workers ->

        WorkersListScreenUIState(
            workers = workers.map { it.name },
            filter = selectable.filter,
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WorkersListScreenUIState()
    )

}

class WorkersListScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkersListScreenViewModel(repository) as T
    }
}