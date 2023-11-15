<p align="center" style="margin:0; padding:0;">
  <h1 align="center" style="margin:0; padding:0;"><img src="https://static.wikia.nocookie.net/logopedia/images/a/a7/Blokus.png" align="center" width="250px"></img></h1>
</p>

## Inhaltsverzeichnis

1. [Intro](#intro)
2. [Spielprinzip](#spielprinzip-1)
3. [Installation](#installation)
4. [Testing](#testing)
5. [Quellen](#quellen)

## Intro

Blokus. Ein Rundenbasierter, zeitloser Klassiker im zweidimensionalen Raum, einfach zu verstehen und noch einfacher zu spielen.

## Spielprinzip [1]

Im Zweipersonenspiel verfügt jeder Spieler über zwei Sätze von 21 Spielsteinen (Polyominos), die sich aus kleinen Quadraten zusammensetzen. Der eine Spieler hat blaue und rote Steine, der andere gelbe und grüne. Dabei kommt jede Form, die aus 1–5 Quadraten besteht, in jeder Farbe genau einmal vor: also 1 Quadrat, 1 Domino, 2 Triominos, 5 Quadraminos und 12 Pentominos. Das Brett besteht aus 20 × 20 quadratischen Vertiefungen, in welche die Spielsteine genau hineinpassen.<br>

Die Spielsteine werden abwechselnd reihum gelegt, wobei in den Ecken begonnen wird. Die Reihenfolge ist im Uhrzeigersinn: Blau – Gelb – Rot – Grün. Der erste Stein jeder Farbe muss so gesetzt werden, dass das Eckfeld besetzt wird. Steine einer Farbe müssen sich immer über Eck berühren, niemals jedoch entlang einer Seite. An fremde Steine kann man dagegen beliebig anlegen.<br>

Es wird so lange gezogen, bis keine Steine mehr gesetzt werden können.<br>

Ziel ist es, möglichst viele Spielsteine auf dem Brett abzulegen. Da das Spielfeld gerade mal ausreichend Platz für alle Steine bietet, werden die Räume schnell eng. Wer am Schluss die wenigsten Punkte übrig hat, hat gewonnen. Dabei zählt jedes kleine Quadrat eines eigenen Spielsteins einen Punkt.
<div align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/1/16/BlockusFinalBoardCloseUp.jpg" width=300px>
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Blokus_starting_in_Tampere.jpg/2560px-Blokus_starting_in_Tampere.jpg" width=300px>
  <img src="https://github.com/Florian11111/blokus/blob/main/DALL·E%202023-11-02%2016.04.51%20-%20Photo%20of%20a%20muscular%20man%20in%20his%2030s%20with%20a%20bald%20head%20and%20pilot%20sunglasses,%20sitting%20at%20a%20table%20playing%20Blokus.%20He%20has%20a%20confident%20smile%20and%20is%20holding%20a.png?raw=true" width=300px>
</div>

## Installation

sbt und so

## Testing

[![Scala CI](https://github.com/Florian11111/blokus/actions/workflows/scala.yml/badge.svg)](https://github.com/Florian11111/blokus/actions/workflows/scala.yml)
[![Coverage Status](https://coveralls.io/repos/github/Florian11111/blokus/badge.svg?branch=main)](https://coveralls.io/github/Florian11111/blokus?branch=main)

Manuell:<br>
sbt clean coverage test<br>
sbt cleanCoverage

## Quellen
[1] https://de.wikipedia.org/wiki/Blokus
