<p align="center" style="margin:0; padding:0;">
  <h1 align="center" style="margin:0; padding:0;"><img src="https://static.wikia.nocookie.net/logopedia/images/a/a7/Blokus.png" align="center" width="250px"></img></h1>
</p>

## Inhaltsverzeichnis

1. [Intro](#intro)
2. [Spielprinzip](#spielprinzip)
3. [Installation](#installation)
4. [Testing](#testing)

## Intro

Blokus. Ein Rundenbasierter, zeitloser Klassiker im zweidimensionalen Raum, einfach zu verstehen und noch einfacher zu spielen.

## Spielprinzip [1]

Im Zweipersonenspiel verfügt jeder Spieler über zwei Sätze von 21 Spielsteinen (Polyominos), die sich aus kleinen Quadraten zusammensetzen. Der eine Spieler hat blaue und rote Steine, der andere gelbe und grüne. Dabei kommt jede Form, die aus 1–5 Quadraten besteht, in jeder Farbe genau einmal vor: also 1 Quadrat, 1 Domino, 2 Triominos, 5 Quadraminos und 12 Pentominos. Das Brett besteht aus 20 × 20 quadratischen Vertiefungen, in welche die Spielsteine genau hineinpassen.<br>

Die Spielsteine werden abwechselnd reihum gelegt, wobei in den Ecken begonnen wird. Die Reihenfolge ist im Uhrzeigersinn: Blau – Gelb – Rot – Grün. Der erste Stein jeder Farbe muss so gesetzt werden, dass das Eckfeld besetzt wird. Steine einer Farbe müssen sich immer über Eck berühren, niemals jedoch entlang einer Seite. An fremde Steine kann man dagegen beliebig anlegen.<br>

Es wird so lange gezogen, bis keine Steine mehr gesetzt werden können.<br>

Ziel ist es, möglichst viele Spielsteine auf dem Brett abzulegen. Da das Spielfeld gerade mal ausreichend Platz für alle Steine bietet, werden die Räume schnell eng. Wer am Schluss die wenigsten Punkte übrig hat, hat gewonnen. Dabei zählt jedes kleine Quadrat eines eigenen Spielsteins einen Punkt.

## Installation

sbt und so

## Testing

[![Scala CI](https://github.com/Florian11111/blokus/actions/workflows/scala.yml/badge.svg)](https://github.com/Florian11111/blokus/actions/workflows/scala.yml)

Manuell:<br>
sbt clean coverage test<br>
sbt cleanCoverage

## Quellen
[1] https://de.wikipedia.org/wiki/Blokus
