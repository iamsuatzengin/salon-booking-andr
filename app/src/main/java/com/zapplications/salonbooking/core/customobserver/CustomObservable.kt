package com.zapplications.salonbooking.core.customobserver

interface CustomObservable<T> {
    val observers: MutableList<CustomObserver<T>>

    fun addObserver(observer: CustomObserver<T>)
    fun removeObserver(observer: CustomObserver<T>)
    fun notifyObservers(item: T)
}

class ObservableClass<T> : CustomObservable<T> {
    override val observers: MutableList<CustomObserver<T>> = mutableListOf()

    override fun addObserver(observer: CustomObserver<T>) {
        observers.add(observer)
    }

    override fun removeObserver(observer: CustomObserver<T>) {
        observers.remove(observer)
    }

    override fun notifyObservers(item: T) {
        observers.forEach { it.notify(item) }
    }
}
