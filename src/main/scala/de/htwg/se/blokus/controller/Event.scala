package de.htwg.se.blokus.controller

sealed trait Event

case object SetupEvent extends Event
case object UpdateEvent extends Event
case object StartGameEvent extends Event
case object PlaceBlockEvent extends Event
case object EndGameEvent extends Event
case object ExitEvent extends Event

