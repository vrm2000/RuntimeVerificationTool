# RuntimeVerificationTool
System Traces Runtime Verification and Analysis. Part of a degree thesis of Computer Science in Universidad de Málaga

## Introducción
Esta aplicación está basada en una herramienta de verificación dinámica en la que se comprueba una propiedad especificada en el lenguaje eLTL (lógica que añade intervalos de eventos) sobre una traza de eventos del sistema. Dicha comprobación se realizará mendiante la creación de un árbol de hebras en las que cada una tendrá un operador de la lógica eLTL. Cada hebra deberá comunicarse con sus hijos y padres para indicar el inicio o el resultado de su evaluación.

## Estructura
La estructura de nuestra implementación cuenta con los siguientes paquetes:
- **Paquete Main:**

Contiene las dos clases principales de nuestra aplicación: Formula y OnlineEvents.
En Formula se llevará a cabo la construcción del árbol y su evaluación.
En OnlineEvents se leerá el fichero que contiene las trazas del sistema y las enviará una a una por un Socket UDP a Formula para que esta la procese.

Adicionalmente, este paquete cuenta con la clase abstracta Node, que indica la estructura que tiene cada nodo del árbol que vamos a crear:
Quien es su hijo izquierdo, hijo derecho y padre. 
Una variable por cada hijo en la que recojas su evaluación y si dicho nodo ha contestado ya o no.

Las trazas con las que cuenta el sistema en ese momento.

Como la lógica es temporal con intervalos de eventos, cuenta con una variable que indica el intervalo de eventos
en los que ese nodo evalua, así como información sobre los indices de la traza de eventos sobre las que tiene que evaluar.

Cada nodo contará con métodos comunes a todos, pero el más destacable es **evaluarCondicion()** que es quien se encarga de comprobar si se va cumpliendo cada subparte en la que se ha dividido la propiedad.

 - **Paquete Operador:**

Este paquete cuenta con todos los tipos de operadores que podemos encontrar por ahora en nuestra herramienta: NOT, OR, AND, IMPLIES, ALWAYS_PQ, ALWAYS_P, EVENTUALLY_PQ, EVENTUALLY_P, PHI.
Este último evalúa condiciones del tipo [Atributo] [Operador booleano] [Valor] (RX_DATA >= 20).

Los operadores temporales son ALWAYS_PQ, ALWAYS_P, EVENTUALLY_PQ, EVENTUALLY_P a los que hay que indicarles el intervalo entre eventos en los que se debe evaluar la propiedad.

 - **Paquete Parsing**
Como se ha mencionado anteriormente, vamos a evaluar una propiedad diviendolas en subpartes que se asociarán a cada nodo del árbol. Este paquete cuenta con los mecanismos necesarios para la lectura y creación del árbol.

Se ha optado por usar de manera conjunta el analizador sintáctico CUP con el analizador léxico JFLEX para la creación de las clases que realizan la transformación.

## Ejecución
Para la ejecución de la herramienta seguimos los siguientes pasos:
 - **Paso 1.** Ejecutamos la clase Formula. Dicha clase requiere de los siquientes archivos: *propiedad.txt*(no necesita especificarse como argumento) y *eltl_property.txt*. El primero indicará la propiedad que evaluará las trazas del sistema especificada en el lenguaje eLTL y el segundo define los atributos de las medidas y los tipos de eventos que tienen las trazas. *eltl_property.txt* debe especificarse como argumento al lanzar la clase. Al indicar el primer fichero, se procede a la creación del árbol.

  - **Paso 2.** Ejecutamos la clase OnlineEvents a la que se le debe pasar como argumento el archivo con las trazas. En nuestro caso lo hemos llamado *events_0.txt*.

  - **Paso 3.** La clase Formula recibirá las trazas enviadas desde OnlineEvents y evaluará la propiedad informando por pantalla de cada evaluación.

## Formula de ejemplo
Se puede modificar el archivo *propiedad.txt* para indicar nuevas propiedades. A continuación veremos algunos ejemplos de propiedades que se pueden crear:
`
Primer ejemplo:
```
always_[stt,stp] RX_DATA >= 5
```
Segundo ejemplo:
```
(Eventually_[stt,stp] RX_DATA > 14) || Always_[l,h] RX_DATA == 30 
```

