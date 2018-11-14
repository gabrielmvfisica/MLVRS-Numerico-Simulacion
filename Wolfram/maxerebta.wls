#!/usr/bin/env wolframscript
(* ::Package:: *)

(* ::Subsection:: *)
(*Con este programa maximizaremos la poblacion total en r del archivo datos el cual se obtiene del programa resuelvea0.wls*)

datostodop=Import["/home/gabrielmv/datos-*.dat","Table"];

fdatos=Interpolation[datostodo];

(*Buscamos los valores de beta que maximizan la población total*)

maxfdatos2[x_, z_] := Maximize[fdatos[x, y, z], {1.1 <= y <= 3}, y];

tabdatos = Flatten[ParallelTable[{x, z, y /. maxfdatos2[x, z][[2]]}, {x, 0, 1, .01}, {z, 0.000001, .0001, .000001}], 1];

(*Lo exportamos al archivo btamax.dat*)
Export["/home/gabrielmv/btamax.dat", tabdatos];

Clear[x,y];

(*Buscamos los valores de r que maximizan la población total*)

maxfdatos2[y_, z_] := Maximize[fdatos[x, y, z], {0 <= x <= 1}, x];

tabdatos = Flatten[ParallelTable[{y, z, x /. maxfdatos2[y, z][[2]]}, {y, 1.1, 3, .01}, {z, 0.000001, .0001, .000001}], 1];

(*Lo exportamos al archivo eremax.dat*)
Export["/home/gabrielmv/eremax.dat", tabdatos];





