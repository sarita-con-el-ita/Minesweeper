DESCOMPOSICIÓN DEL PROBLEMA

En primer lugar, nos pusimos a analizar cuales son las cosas con las que interactúa un jugador en el buscaminas original, tras varias partidas y preguntas llegamos a cosas clave como: 
El Inicio del juego (donde se escoge la dificultad y por ende las dimensiones del tablero)
El Tablero (formado por casillas) 
Las Casillas (que pueden ser descubierta o no descubierta, que a su vez pueden ser minas, números o vacías) 
Las banderas (que tienen que poder ponerse y quitarse en cualquier casilla no descubierta)
El Fin del juego (cuando descubres una mina o cuando ya pusiste todas tus banderas y no tienes más casillas por descubrir)
El tiempo (lo que tardas en perder o ganar medido en segundos)
El Registro (donde nos encargaremos de acumular partidas conforme el jugador juegue) 
Con todo eso dicho decidimos hacer 7 clases que contendrían al juego entero, siendo estas InicioJuego, Tablero, Casilla, Mina, SinMina, Registro y Fin. 


DECISIONES DE DISEÑO SOBRE LAS CLASES

InicioJuego
Esta va a ser la clase que le va a dar la bienvenida al jugador por así decirlo, esta va a ser la encargada de pedir al usuario que defina sus acciones, como iniciar partida o pedir el registro, dependiendo de lo que escoja el jugador, esta clase tiene una relación de composición con Tablero, y adicionalmente una agregación con Registro.
Esto permite que registro y tablero sean utilizados como atributos para la clase, la lógica de no usar otras relaciones como herencia, es que registro puede existir sin InicioJuego y viceversa, y compone tablero porque el tablero depende del InicoJuego, sin embargo, no es un InicioJuego.
Tablero
Tablero es un arreglo bidimiensional estático, ya que, no cambia sus dimensiones uns vez elegidas. Cuando el jugador ya ha elegido su dificultad, el tablero se va a imprimir con las dimensiones seleccionadas por la dificultad, ahora como es difícil de explicar en palabras me tome la libertad de agregar una foto de apoyo visual para entender mejor el tablero, mientras, hay que dejar claro que decidimos no identificar las casillas como coordenadas porque eso haría incomodo a la vista el tablero, por tal motivo decidimos usar números empezando desde el 10.
<img width="482" height="478" alt="image" src="https://github.com/user-attachments/assets/764001c2-f823-45b1-ac13-4eb709553b1b" />
Para ejemplificar usaremos el nivel fácil - aunque aplica para todos los niveles – iniciamos en el 10, donde normalmente seria nuestra coordenada (0,0)  y finaliza en el 90, donde normalmente seria la coordenada (8,8) tomamos estos valores porque decidimos reservar del 0 al 8 como números cercanos a la mina, siendo 0 si no tiene minas alrededor, por ende, vacía, y el 8 porque es el número máximo que puede tomar una casilla, al tener 8 minas alrededor, el nueve decidimos excluirlo por decisiones estéticas, ya que se ve más entendible si empezamos desde el 10, ahora bien, por qué acaba en 90? Acaba en noventa porque es el resultado de el número de filas multiplicado el número de columnas, más nueve. El más nueve proviene de no usar del 1 al 9 en la numeración del tablero.
Tablero tiene una relación de composición con Casilla <Abstract>, esto porque el tablero no es una casilla, sin embargo, si depende de estas para existir. 
Importante, el tablero una limitación técnica directamente relacionada con el tamaño de la terminal, por tal motivo, si se quiere hacer un tablero muy grande este se va a imprimir de una forma rara que no permite el entendimiento claro del tablero. Cabe aclarar que nuestro código el primer toque no está protegido, es decir alguien puede descubrir una mina en el primer intento si corre con mucha mala suerte, además tampoco esta protegida para numero solos, esto implica que hay veces en la que el primer descubrimiento será, un numero imposible de determinar dónde está la mina.
Casilla <Abstract>
Casilla es una clase que solo se puede explicar a través de sus instancias, en este caso Mina y SinMina, curiosamente estas son clases que heredan de Casilla, digo todo esto porque una casilla puede estas sin descubrir o descubierta, al ser descubierta, o instanciarla solo puede tomar 2 estados, hay mina o no hay mina, nunca podrá instanciarse como una casilla, por tal motivo, esta clase Casilla es una clase abstracta que obligara a sus clases que heredan a instanciar. Adicionalmente Casilla determina la capacidad de poner bandera o no, ya que uno solo puede poner la bandera en una casilla no descubierta. Recordar que en cada partida hay un número igual de banderas al número de minas.
Definimos que no íbamos a poner la opción de poner un signo de pregunta en las casillas que no estaba seguro el jugador, ya que como no fue requisito preferimos no complicarnos con eso.
Mina
Esta como dije previamente hereda de Casilla, heredando todos sus atributos, sin involucrar uno propio, ya que no los necesita, lo que cambia es que hace override sobre 2 métodos abstractos de Casilla, esto permite que Mina los use para definir si es o no es, y determinar cuándo pierde el jugador.
SinMina 
En cambio, SinMina si tiene atributos propios, pero también hace override sobre los mismos 2 métodos abstractos de Casilla, sus atributos propios serán cuando este vacía y cuando este con minas alrededor en forma de contador. Acá una aclaración importante, el tablero será el encargado de hacer el efecto cascada sobre las minas vacías.
Registro
Registro es un arreglo dinámico para que siempre entren partidas nuevas sin límite. Para el registro de partidas se nos solicita que este muestre el registro al acabar la partida o cuando el jugador lo solicite, y que además el jugador lo pueda organizar por algún criterio. Tomando esto en cuenta nuestro registro fue definido para que solo aparezca cuando el jugador lo solicite, y se podrá organizar a partir de 4 criterios, estos son: número de partida, resultado de las partidas, nivel de dificultad y tiempo. Posteriormente nos dimos cuenta de que buscar por tiempo es un poco inútil porque tienes que saber de memoria el tiempo exacto, por lo que es un poco maluco, sin embargo, ahí esta y se puede usar. adicionalmente en el registro cuenta salirse como perder, o sea, “DERROTA”
Para la organización del registro se uso un bubblersort para los: número de partida, resultado de las partidas y nivel de dificultad y se usa un binarysearch para buscar el tiempo.
Esta clase tiene una relación de agregación con InicioJuego.
Fin
Esta clase funciona para justamente determinar si perdió o no el jugador, y más importante, el tiempo, el tiempo solo se mostrará al finalizar una partida, sin embargo, este solo se mostrará si pierde o gana, no si se sale de la partida, si se quiere saber se puede hacer a través del historial directamente.
Fin tiene una relación de agregación a InicioJuego.

COMANDOS

A la hora de jugar creamos 4 comandos con los que el jugador interactúa dentro de partida
Descubrir: este tendrá como letra clave la d minúscula y acto seguido la casilla que quiera descubrir, por ejemplo: “d 10” las comillas no se utilizan sin embargo el espacio es estrictamente necesario.
Poner bandera: este tendrá como letra clave p minúscula y la casilla sobre la cual se puede y quiere poner bandera, recordar que no se puede poner bandera sobre una casilla previamente descubierta, por ejemplo: “p 10” las comillas no se utilizan sin embargo el espacio es estrictamente necesario. En pantalla se vera como una P mayúscula, que asemeja a una bandera en el juego original.
Quitar bandera: este tendrá como letra clave x minúscula y la casilla sobre la cual se puede y quiere poner bandera, recordar que no se puede quitar una bandera de una casilla que no tiene bandera, por lo que ejecutar el comando en una casilla sin bandera no hará nada, por ejemplo: “p 10” las comillas no se utilizan sin embargo el espacio es estrictamente necesario. Adicionalmente al ejecutar el comando la bandera volver al contador de banderas.
Salir de partida: este es el más sencillo, solo tendrás que escribir “salir” sin espacios ni las comillas, y esto hará que te salgas de la partida 

FLUJO NATURAL DEL JUEGO

Primero que nada, al no poderse ningún tipo de interfaz todo tiene que ser a través de unos comandos, estas acciones que puede hacer el jugador son varias, por ejemplo, este puede, iniciar una partida, pedir el historial de partidas o salirse del juego, al inicio poniendo 1, 2 o 3 respectivamente en la terminal apenas le dé a ejecutar, 
Si decide iniciar una partida le pedirá elegir una dificultad, al hacer eso, podrá elegir la dificultad que quiera, en caso de fácil, medio y difícil tanto las minas como las dimensiones del tablero ya estarán predefinidos, en caso de escoger personalizado, el jugador definirá las dimensiones con un mínimo de 2x2, sin máximo y también definirá las minas de que desee, estas como limite puede ser iguales al número total de casillas – 1, por ejemplo en un caso 2x2 las casillas posibles son 4 pero solo puedes poner 3 minas. 
En la partida puede perder, ganar o salirte, estas opciones califican como el fin de la partida y te devolverá al menú principal donde podrás repetir el ciclo o ver el historial, si decides ver el historial podrás ver el historial y abajo podrás ver el menú para organizar y buscar el historial de la forma en la que se desee, posteriormente puede salirte del historial y salirte del juego.  

UML

<img width="921" height="402" alt="image" src="https://github.com/user-attachments/assets/5f6bb266-afbe-4990-b062-2dbc0515d345" />
