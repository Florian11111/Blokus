package de.htwg.se.blokus.util

trait Observer[T] {
    def update(event: T): Unit
}

trait Observable[T] {
    private var observers: List[Observer[T]] = List()
    def addObserver(observer: Observer[T]): Unit = {
        observers = observer :: observers
    }
    def removeObserver(observer: Observer[T]): Unit = {
        observers = observers.filterNot(_ == observer)
    }
    def notifyObservers(event: T): Unit = {
        observers.foreach(_.update(event))
    }
}