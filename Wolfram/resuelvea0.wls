#!/usr/bin/env wolframscript

ClearAll;

(* Definimos algunos parámetros del sistema: Ka=capacidad de carga, lambdap=tasa de depredación, sig= sigma tasa de crecimiento
de presas, Rp= Tamaño de la celda en el modelo de malla, alpha= alpha tasa de movimiento de los depredadores*)
Ka = 1.;
lambdap = 1.;
sig = 1.;
Rp = 10.;
alpha = 1;

(*Distribución de ley de potencias de los pasos del depredador (eq. 18*)

f[x_, bt_] := 
  Piecewise[{{Abs[x]^(-bt)/(2*Zeta[bt]), Abs[x] >= 1}, {0, 
     Abs[x] < 1}}];

(*Transformada de Fourier de la funcion f(k)*)

fk[k_, bt_] := FourierSequenceTransform[f[n, bt], n, k];


(*Probabilidad de que el depredador se mantenga en la misma celda "p(0)=p0" (eq. 17)*)

p0[bt_] := 
  Piecewise[{{1 - 1/Rp + (1 - Rp^(2 - bt))/((2 - bt)*Rp), 
     bt != 2}, {1 - 1/Rp - Log[Rp]/Rp, bt == 2}}];

(*Integral1 auxiliar para calcular la densidad a_0*)

i1[r_, bt_, mu_, lambda1_] := 
  ((mu + r)*lambda1/(2 Pi))*NIntegrate[1/(alpha(1 - p0[bt])*(1 - fk[k, bt]) + mu + r), {k, -Pi, Pi}];

(*Densidad de depredadores a_0 (eq. 12)*)
a0[r_?NumericQ, bt_?NumericQ, mu_?NumericQ,lambda1_?NumericQ] := (1/(1 + Ka*lambdap/sig))*(Ka - mu/i1[r, bt, mu, lambda1]);

(*Población total de depredadores N_p (eq. 10)*)
Np[r_?NumericQ, bt_?NumericQ, mu_?NumericQ,lambda1_?NumericQ] := Rp*(lambda1*a0[r, bt, mu,lambda1]/mu)*(Ka-a0[r, bt, mu,lambda1]*(1 + Ka*lambdap/sig));

(*Hacemos un barrido sobre el intervalo de interes donde se quiere calcular la población, en este caso, variamos lambda y guardamos en un archivo la población total como función de los parámetros r, beta, lambda y mu*)
Do[

lamb=.001*i;
dmu=(lamb-.0001)/200;

  data = Flatten[ParallelTable[{ere,bta,mu,lamb,Np[ere,bta,mu,lamb]},{mu,0.0001,lamb,dmu},{bta, 1.1, 3, .1},{ere,0,1,.01}], 2];
  
(*Guardamos los datos obtenidos en la siguiente carpeta*)

Export[StringJoin["/home/gabrielmv/datosmubta/datos-",ToString[i],".dat"], data];

,{i,1,10}];


  
