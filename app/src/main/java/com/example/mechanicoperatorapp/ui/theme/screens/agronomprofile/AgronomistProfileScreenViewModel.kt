package com.example.mechanicoperatorapp.ui.theme.screens.agronomprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mechanicoperatorapp.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class AgronomistProfileScreenUIState(
    val name: String = "",
)

data class SelectableUIState(
    val expanded: Boolean = true,
)

class AgronomistProfileScreenViewModel(
    private val repository: AppRepository
) : ViewModel() {
    private val selectableUIState = MutableStateFlow(
        SelectableUIState()
    )

    val uiState: StateFlow<AgronomistProfileScreenUIState> = combine(
        selectableUIState,
        repository.getWorkMans()
    ) { selectable, agronomistprofileScreenData ->

        AgronomistProfileScreenUIState(
            name = "agronomistprofileScreenData.name",
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AgronomistProfileScreenUIState()
    )

}

class AgronomistProfileScreenViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgronomistProfileScreenViewModel(repository) as T
    }
}
