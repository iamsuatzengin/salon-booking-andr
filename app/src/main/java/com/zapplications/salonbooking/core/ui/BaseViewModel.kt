package com.zapplications.salonbooking.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.core.coroutineflow.runCoroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _baseUiEvent = MutableSharedFlow<UiEvent>()
    val baseUiEvent = _baseUiEvent.asSharedFlow()

    /**
     * For child view models to send events to the UI
     */
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun <T> call(
        block: suspend () -> T,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: (T) -> Unit,
    ) {
        viewModelScope.launch {
            runCoroutine(
                onLoading = {
                    _loadingState.update { true }
                },
                onError = { throwable ->
                    _baseUiEvent.emit(ShowError(throwable.message.toString()))
                    onError?.invoke(throwable)
                },
                onCompletion = { throwable ->
                    _loadingState.update { false }

                    if (throwable != null) {
                        _baseUiEvent.emit(ShowError(throwable.message.toString()))
                        onError?.invoke(throwable)
                    }
                },
                block = block
            ).collect {
                if (it != null) onSuccess(it)
            }
        }
    }

    fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}

data class ShowError(val message: String) : UiEvent
