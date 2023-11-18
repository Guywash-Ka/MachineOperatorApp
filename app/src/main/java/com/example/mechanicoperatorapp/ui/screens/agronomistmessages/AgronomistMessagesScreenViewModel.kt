package com.example.mechanicoperatorapp.ui.screens.agronomistmessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mechanicoperatorapp.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class AgronomistMessagesScreenUIState(
    val messages: List<String> = emptyList(),
)

data class SelectableUIState(
    val filter: Int = 0,
)

class AgronomistMessagesScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<AgronomistMessagesScreenUIState> = combine(
        selectableUIState,
        repository.getWaters()
    ) { selectable, agronomistmessagesScreenData ->

        AgronomistMessagesScreenUIState(
            messages = emptyList(),
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AgronomistMessagesScreenUIState()
    )

}

class AgronomistMessagesScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgronomistMessagesScreenViewModel(repository) as T
    }
}