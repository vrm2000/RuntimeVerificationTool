# RuntimeVerificationTool 馃捇鈿欙笍

## Introducci贸n
Esta aplicaci贸n est谩 basada en una herramienta de verificaci贸n din谩mica en la que se comprueba una propiedad especificada en el lenguaje eLTL (l贸gica que a帽ade intervalos de eventos) sobre una traza de eventos del sistema. Dicha comprobaci贸n se realizar谩 mendiante la creaci贸n de un 谩rbol de hebras en las que cada una tendr谩 un operador de la l贸gica eLTL. Cada hebra deber谩 comunicarse con sus hijos y padres para indicar el inicio o el resultado de su evaluaci贸n.

## Estructura
La estructura de nuestra implementaci贸n cuenta con los siguientes paquetes:
- **Paquete Main:**

Contiene las dos clases principales de nuestra aplicaci贸n: **Formula** y **OnlineEvents**.
En Formula se llevar谩 a cabo la construcci贸n del 谩rbol y su evaluaci贸n.
En OnlineEvents se leer谩 el fichero que contiene las trazas del sistema y las enviar谩 una a una por un Socket UDP a Formula para que esta la procese.

Adicionalmente, este paquete cuenta con la clase abstracta Node, que indica la estructura que tiene cada nodo del 谩rbol que vamos a crear:
Quien es su hijo izquierdo, hijo derecho y padre. 
Una variable por cada hijo en la que recojas su evaluaci贸n y si dicho nodo ha contestado ya o no.

Como la l贸gica es temporal con intervalos de eventos, cuenta con una variable que indica el intervalo de eventos
en los que ese nodo evalua, as铆 como informaci贸n sobre los indices de la traza de eventos sobre las que tiene que evaluar.

Cada nodo contar谩 con m茅todos comunes a todos, pero el m谩s destacable es **evaluarCondicion()** que es quien se encarga de comprobar si se va cumpliendo cada subparte en la que se ha dividido la propiedad.

 - **Paquete Operador:**

Este paquete cuenta con todos los tipos de operadores que podemos encontrar por ahora en nuestra herramienta: NOT, OR, AND, IMPLIES, ALWAYS_PQ, ALWAYS_P, EVENTUALLY_PQ, EVENTUALLY_P, PHI.
Este 煤ltimo eval煤a condiciones del tipo [Atributo] [Operador booleano] [Valor] (RX_DATA >= 20).

Los operadores temporales son ALWAYS_PQ, ALWAYS_P, EVENTUALLY_PQ, EVENTUALLY_P a los que hay que indicarles el intervalo entre eventos en los que se debe evaluar la propiedad.

 - **Paquete Parsing**
Como se ha mencionado anteriormente, vamos a evaluar una propiedad diviendolas en subpartes que se asociar谩n a cada nodo del 谩rbol. Este paquete cuenta con los mecanismos necesarios para la lectura y creaci贸n del 谩rbol.

Se ha optado por usar de manera conjunta el analizador sint谩ctico CUP con el analizador l茅xico JFLEX para la creaci贸n de las clases que realizan la transformaci贸n.

## Ejecuci贸n
Para la ejecuci贸n de la herramienta se recomienda seguir los siguientes pasos
 - **Paso 1.** Lanzar el script `rvt.sh` con los siguientes argumentos: *eltl_property.txt*, *propiedad.txt* y Y/N. El primero define los atributos de las medidas y los tipos de eventos que tienen las trazas, el segundo indicar谩 la propiedad que evaluar谩 las trazas del sistema especificada en el lenguaje eLTL y el tercero si se desea obtener un resumen de la ejecuci贸n por pantalla (N) o en el fichero log.txt (Y). Si se ejecuta sin argumentos, estos se solicitan al usuario por pantalla. El script se encarga de preparar todos los archivos necesarios y llamar a las clases java correspondientes
```
./rvt.sh eltl_property.txt propiedad.txt N
```
  - **Paso 2.** Ejecutamos OnlineEvents.jar a la que se le debe pasar como argumento el archivo con las trazas. En nuestro caso lo hemos llamado *events_0.txt*. Se puede ejecutar de la siguiente forma:
```
java -jar OnlineEvents.jar events0.txt
```

  - **Paso 3.** El script devolver谩 el veredicto por pantalla o abriendo el fichero de log generado.

## Formula de ejemplo
El lenguaje eLTL permite expresas f贸rmulas de la siguiente forma:
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
Se incluye con el c贸digo fuente la carpeta `tests` con propiedades, ficheros de eventos y de medidas de prueba (los ficheros de medida se especifican en el fichero *eltl_property.txt*. Es necesario que el fichero de medidas y el de eventos est茅 relacionado, ya que el programa no podr铆a funcionar en caso contrario. Se puede probar una ejecuci贸n de la siguiente forma:

```
$ ./rvt.sh tests/eltl_property.txt tests/f6.txt N
$ java -jar OnlineEvents.jar tests/events_6.txt
```
El resultado de la evaluaci贸n es `false` para los ficheros de trazas de eventos y medidas especificados
