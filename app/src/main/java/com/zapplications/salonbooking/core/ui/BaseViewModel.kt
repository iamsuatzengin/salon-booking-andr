package com.zapplications.salonbooking.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.core.coroutineflow.ApiErrorModel
import com.zapplications.salonbooking.core.coroutineflow.AppException
import com.zapplications.salonbooking.core.coroutineflow.runCoroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _baseUiEvent = MutableSharedFlow<UiEvent>()
    val baseUiEvent = _baseUiEvent.asSharedFlow()

    /**
     * For child view models to send events to the UI
     */
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    /**
     * use this method in child view models for long running process like API call.
     */
    fun <T> call(
        block: suspend () -> T,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            runCoroutine(
                onLoading = { _loadingState.update { true } },
                onError = { throwable ->
                    if (onError != null) {
                        onError.invoke(throwable)
                    } else if (throwable is AppException) {
                        _baseUiEvent.emit(ShowApiError(throwable.errorModel))
                    } else {
                        _baseUiEvent.emit(ShowError(throwable.message.toString()))
                    }

                    Firebase.crashlytics.recordException(throwable)
                },
                onCompletion = { throwable ->
                    _loadingState.update { false }

                    if (throwable != null) {
                        if (onError != null) {
                            onError.invoke(throwable)
                        } else if (throwable is AppException) {
                            _baseUiEvent.emit(ShowApiError(throwable.errorModel))
                        } else {
                            _baseUiEvent.emit(ShowError(throwable.message.toString()))
                        }

                        Firebase.crashlytics.recordException(throwable)
                    }
                },
                block = block
            ).collect {
                if (it != null) onSuccess?.invoke(it)
            }
        }
    }

    fun <T> callWithoutLoading(
        block: suspend () -> T,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            runCoroutine(
                onError = { throwable ->
                    if (onError != null) {
                        onError.invoke(throwable)
                    } else if (throwable is AppException) {
                        _baseUiEvent.emit(ShowApiError(throwable.errorModel))
                    } else {
                        _baseUiEvent.emit(ShowError(throwable.message.toString()))
                    }

                    Firebase.crashlytics.recordException(throwable)
                },
                onCompletion = { throwable ->
                    if (throwable != null) {
                        if (onError != null) {
                            onError.invoke(throwable)
                        } else if (throwable is AppException) {
                            _baseUiEvent.emit(ShowApiError(throwable.errorModel))
                        } else {
                            _baseUiEvent.emit(ShowError(throwable.message.toString()))
                        }

                        Firebase.crashlytics.recordException(throwable)
                    }
                },
                block = block
            ).collect {
                if (it != null) onSuccess?.invoke(it)
            }
        }
    }

    fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}

data class ShowError(val message: String) : UiEvent
data class ShowApiError(val errorModel: ApiErrorModel) : UiEvent
