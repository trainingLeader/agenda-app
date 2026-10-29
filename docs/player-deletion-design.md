# Diseño del borrado de jugadores

## Entrada y presentación

La opción `2. Remove Player` de PlayerMenu abrirá un JDialog modal y centrado. Reutilizará la tabla y el filtro del buscador: nombre, apellido, edad, posición, país y camiseta, con búsqueda inmediata por nombre o apellido y contador de resultados. El título y las instrucciones indicarán que se está en la pantalla de eliminación.

## Confirmación y eliminación

- Al seleccionar una fila se abrirá una confirmación que identificará al jugador por nombre, apellido y camiseta.
- La confirmación tendrá las opciones Sí y No, con No como opción inicial.
- Solo Sí eliminará el jugador de PlayerRegistry.PLAYERS mediante su clave de camiseta y retirará la fila de la tabla.
- No o cerrar la confirmación conservarán el mapa y la tabla. Se limpiará la selección para permitir volver a seleccionar al mismo jugador.
- Tras eliminar, el diálogo permanecerá abierto, se conservarán el texto de búsqueda y la ordenación, y se actualizará el contador.
- Se identificará al jugador desde la fila del modelo, convirtiendo el índice visible si la tabla está filtrada u ordenada, para evitar borrar otra persona.
- Actualizar, filtrar o retirar filas no abrirá confirmaciones adicionales ni eliminará automáticamente al jugador que pase a ocupar la fila seleccionada.
- Sin jugadores o sin coincidencias, la tabla estará vacía y no se solicitará ninguna confirmación.
- Cerrar, Esc o el botón de cierre terminarán el diálogo sin eliminar otros jugadores.

## Enfoque

Se ampliará el panel de búsqueda para admitir el comportamiento de eliminación con confirmación, conservando el buscador existente como consulta. Esto evita duplicar la tabla y el filtro en un segundo panel. Una tabla independiente sería más aislada, pero repetiría la carga y búsqueda de jugadores.

## Verificación

Pruebas de aceptación y cancelación del borrado, eliminación del jugador correcto con filtro y ordenación, conservación del filtro y actualización del contador, ausencia de confirmaciones encadenadas, tabla vacía y regresión del buscador de consulta. Se comprobará la ventana y su confirmación en un entorno gráfico si está disponible.
