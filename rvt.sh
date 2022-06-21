#!/bin/bash
# muestra infomacion para el comando help
function muestra_uso {
  echo "Esta aplicación está basada en una herramienta de verificación dinámica en la que se comprueba una propiedad especificada en el lenguaje eLTL (lógica que añade intervalos de eventos) sobre una traza de eventos del sistema.
Para su ejecución puede especificar los siguientes argumentos, en orden. Si no especifica ninguno, la aplicacion preguntará por ellos
	1) <eltl_property.txt> indica el fichero de especificacion de propiedades.
	2) <property.txt> indica el fichero con una propiedad expresada en eLTL
	3) Y Si desea imprimir en un fichero de log la información de la ejecucion, o N si prefiere por pantalla
El fichero de especificacion propiedades define los nombres de los eventos (usando \#events), las medidas (usando \#define) y el fichero de medidas (usando ##define MEASURES_FILE \"<measures.txt>\").
El fichero de la propiedad contiene una propiedad expresada en eLTL. Ejemplo: A_{stt} true && E_{h,l} rx_data > 0
Para más información, compruebe el manual de uso en la memoria."
exit 1;
}

# muestra informacion simplificada en el caso de error
function muestra_error {
  echo "Error. Debe especificar tres argumentos o llamar a help. Si quiere continuar sin especificar nada, llame directamente al script"
exit 1;
}

function crea_archivos {
#Creamos ficheros temporales y log si no existen
if [ ! -f arbol.txt ]; then
	touch arbol.txt
fi

if [ ! -f log.txt ]; then
	touch log.txt
fi
}


if [ $# -eq 3 ]; then # si se introducen tres argumentos...
  	crea_archivos
  	echo "Lanzando aplicación con argumentos..."
	java -jar ./target/RuntimeVerificationTool-jar-with-dependencies.jar $1 $2 $3 # lanzamos aplicacion con argumentos
elif [ $# -eq 1 ]; then
	if [ $1 == 'help' ]; then
	muestra_uso
	else
	muestra_error
	fi
else
  	crea_archivos
  	echo "Lanzando aplicación sin argumentos (puede especificarlos como argumento. Consulte help para más información)..."
	java -jar ./target/RuntimeVerificationTool-jar-with-dependencies.jar # lanzamos aplicacion sin argumentos
fi

#Borramos fichero temporal
rm arbol.txt