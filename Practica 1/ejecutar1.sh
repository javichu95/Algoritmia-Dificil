#!/bin/bash
javac practica1/*.java
java practica1.Amazon -alg karger
java practica1.Amazon -alg karger_Stein
java practica1.Amazon -alg karger_steinPesos
java practica1.Amazon -alg kargerPesos
java practica1.Amazon -ejemplo 1
java practica1.Amazon -ejemplo 2
java practica1.Amazon -ejemplo 3
java practica1.Amazon -ejemplo 4
rm practica1/*.class
