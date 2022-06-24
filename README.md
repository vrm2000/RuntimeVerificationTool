# RuntimeVerificationTool 💻⚙️

## Introducción
Esta aplicación está basada en una herramienta de verificación dinámica en la que se comprueba una propiedad especificada en el lenguaje eLTL (lógica que añade intervalos de eventos) sobre una traza de eventos del sistema. Dicha comprobación se realizará mendiante la creación de un árbol de hebras en las que cada una tendrá un operador de la lógica eLTL. Cada hebra deberá comunicarse con sus hijos y padres para indicar el inicio o el resultado de su evaluación.

## Estructura
La estructura de nuestra implementación cuenta con los siguientes paquetes:
- **Paquete Main:**

Contiene las dos clases principales de nuestra aplicación: **Formula** y **OnlineEvents**.
En Formula se llevará a cabo la construcción del árbol y su evaluación.
En OnlineEvents se leerá el fichero que contiene las trazas del sistema y las enviará una a una por un Socket UDP a Formula para que esta la procese.

Adicionalmente, este paquete cuenta con la clase abstracta Node, que indica la estructura que tiene cada nodo del árbol que vamos a crear:
Quien es su hijo izquierdo, hijo derecho y padre. 
Una variable por cada hijo en la que recojas su evaluación y si dicho nodo ha contestado ya o no.

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
Para la ejecución de la herramienta se recomienda seguir los siguientes pasos
 - **Paso 1.** Lanzar el script `rvt.sh` con los siguientes argumentos: *eltl_property.txt*, *propiedad.txt* y Y/N. El primero define los atributos de las medidas y los tipos de eventos que tienen las trazas, el segundo indicará la propiedad que evaluará las trazas del sistema especificada en el lenguaje eLTL y el tercero si se desea obtener un resumen de la ejecución por pantalla (N) o en el fichero log.txt (Y). Si se ejecuta sin argumentos, estos se solicitan al usuario por pantalla. El script se encarga de preparar todos los archivos necesarios y llamar a las clases java correspondientes
```
./rvt.sh eltl_property.txt propiedad.txt N
```
  - **Paso 2.** Ejecutamos OnlineEvents.jar a la que se le debe pasar como argumento el archivo con las trazas. En nuestro caso lo hemos llamado *events_0.txt*. Se puede ejecutar de la siguiente forma:
```
java -jar OnlineEvents.jar events0.txt
```

  - **Paso 3.** El script devolverá el veredicto por pantalla o abriendo el fichero de log generado.

## Formula de ejemplo
El lenguaje eLTL permite expresas fórmulas de la siguiente forma:
Primer ejemplo:
```
always_{stt,stp} RX_DATA_MB >= 5
```

Segundo ejemplo:
```
(E_[stt,stp] RX_DATA_B > 14) || Always_{l,h} RX_DATA_MB == 30 
```

Tercer ejemplo:
```
((Eventually_[stt,stp] RSSI == 0) || A_[l,l] true) IMPLIES ! E_{h} rx_data_b > 0
```
## Ficheros de muestra
Se incluye con el código fuente la carpeta `tests` con propiedades, ficheros de eventos y de medidas de prueba (los ficheros de medida se especifican en el fichero *eltl_property.txt*. Es necesario que el fichero de medidas y el de eventos esté relacionado, ya que el programa no podría funcionar en caso contrario. Se puede probar una ejecución de la siguiente forma:

```
$ ./rvt.sh tests/eltl_property.txt tests/f6.txt N
$ java -jar OnlineEvents.jar tests/events_6.txt
```
El resultado de la evaluación es `false` para los ficheros de trazas de eventos y medidas especificados
