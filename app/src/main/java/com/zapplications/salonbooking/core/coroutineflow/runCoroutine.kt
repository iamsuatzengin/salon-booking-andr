package com.zapplications.salonbooking.core.coroutineflow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Executes a suspend function in a coroutine flow and provides lifecycle hooks for loading, error, and completion events.
 *
 * This function runs the provided [block] as a coroutine that emits its result. It also offers callbacks:
 * - [onLoading] is invoked before the block execution starts. A delay of 500 milliseconds is applied after invoking onLoading.
 * - [onError] is called if the execution of [block] throws an exception. In such a case, a `null` value is emitted.
 * - [onCompletion] is executed after the flow completes, receiving any throwable that may have occurred (or null if completed successfully).
 *
 * The entire flow operates on the specified [dispatcher] (defaults to [Dispatchers.IO]).
 *
 * @param dispatcher The CoroutineDispatcher to run the flow on. Defaults to [Dispatchers.IO].
 * @param onLoading A lambda to be executed before the block is invoked, typically used to update UI to show a loading state.
 * @param onError A suspend lambda to handle any Throwable that might be thrown during the execution of [block]. Emitted value will be null on error.
 * @param onCompletion A suspend lambda that is called after the flow completes, with an optional Throwable parameter if an error occurred.
 * @param block A suspend function that returns a nullable result of type [T]. The result is emitted as the flow's single emission.
 *
 * @return A flow emitting a single value from [block], or `null` in case of an error. Lifecycle callbacks are invoked at appropriate stages.
 */
fun <T> runCoroutine(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    onLoading: () -> Unit = {},
    onError: suspend (Throwable) -> Unit = {},
    onCompletion: suspend (Throwable?) -> Unit = {},
    block: suspend () -> T?,
) = flow {
    emit(block())
}.onStart {
    onLoading()
    delay(500)
}.catch { throwable ->
    onError(throwable)
    emit(null)
}.onCompletion { throwable ->
    onCompletion(throwable)
}.flowOn(dispatcher)
