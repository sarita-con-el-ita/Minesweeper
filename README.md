# Juego Buscaminas

Nuestro diseño consta de 7 clases las cuales son: *InicioJuego*, *Registro*, *Tablero*, *Casillas*, *Mina*, *SinMina*, y *Fin*.
---

## ¿Cómo empezar a jugar?
En el archivo de *InicioJuego* le debes dar click a **run**, luego te aparecerá la terminal. Allí tienes el menú principal donde tienes varias opciones para elegir:
**1**. Nueva Partida
**2**. Historial
**3**. Salir
   
Siendo así debes digitar uno de los 3 números, vamos a suponer que digitas el número **1**, entonces las partidas tienen 3 niveles diferentes de dificultad:
**1**.Fácil(9 x 9, con 10 minas)
**2**. Medio (16 x 16, con 40 minas)
**3**. Difícil (16 x 30, con 99 minas)
**4**.Personalizado También tienes esta opción para elegir el tamaño de tablero que desees con sus respectivas minas
Luego de ser digitado el número correspondiente al nivel que deseas, se creará el tablero

----

## ¿Cómo funciona el tablero?
El tablero está configurado  de la siguiente forma:
La casilla inicial está enumerada con el número **10**, es decir, arrancamos con el número diez, la casilla de al lado es la **11**, y así hasta terminar la fila. En la siguiente fila el número no se reinicia, sino que **continúa la secuencia**
* *Ejemplo:* En el caso del nivel fácil, si la primera fila terminó en el numero **18**, la siguiente fila comenzará abajo con el número **19**.
  ---

## El usuario interactúa con el tablero a través de los siguientes símbolos:
* d [numero] para **descubrir** casilla
* p [numero] para **poner** bandera
* x [numero] para **quitar** bandera
* salir para **salir** de la partida actual y regresar al menú.
Entonces el usuario empezará a digitar, por ejemplo, **d 10** y así sucesivamente para jugar de acuerdo a sus decisiones de lógica 

## ¿Cómo se gana o se pierde?
* **Para ganar:** Tienes que lograr descubrir todas las casillas seguras (las que no tienen mina) del tablero. Las únicas que deben quedar con la bandera, es decir con p, son las que tienen minas.Si lo logras el juego te felicitará y te dirá cuántos segundos te tomó ganar.
* **Para perder:** Si digitas descubrir en una casilla que tiene una mina (por ejemplo, d 25, y ahí había una mina), el tablero explotará, y se revelarán todas las minas en la pantalla para que veas dónde estaban y el juego terminará.

## Protección del Primer Turno
El juego tiene un escudo protector: primero te pide que digites tu primera casilla y **DESPUÉS** acomoda las minas en las demás casillas.Así que es 100% imposible que pierdas en tu primer intento.
