# Diseño del buscador de jugadores

## Entrada y ventana

La opción `3. Search Player` de `PlayerMenu` abrirá un `PlayerSearchDialog` centrado y modal, siguiendo el comportamiento del formulario de registro. Tendrá un `PlayerSearchPanel` con campo de búsqueda, tabla, contador de resultados y botón Cerrar. Esc y el botón de cierre de la ventana también cerrarán el diálogo.

## Datos y búsqueda

- Se mostrarán los jugadores de `PlayerRegistry.PLAYERS` al abrir la ventana.
- La JTable tendrá seis columnas: nombre, apellido, edad, posición, país y camiseta.
- La tabla será de consulta, sin edición de celdas.
- Con el buscador vacío se mostrarán todos los jugadores.
- Cada cambio del texto actualizará inmediatamente las filas visibles, sin botón Buscar.
- La coincidencia será parcial sobre el nombre y apellido combinados, sin distinguir mayúsculas y minúsculas. Se ignorarán los espacios al inicio y al final de la consulta.
- Los caracteres especiales de la consulta se tratarán como texto literal.
- Si no hay jugadores o coincidencias, se mostrará la tabla vacía y un contador de cero resultados.
- Al reabrir el diálogo se cargarán los jugadores registrados desde la apertura anterior. La búsqueda no modificará el HashMap.

## Implementación elegida

Se usará un modelo de tabla no editable y un `TableRowSorter` con un filtro literal actualizado por un `DocumentListener`. Esto aprovecha las capacidades de Swing y permite ordenar por columnas. Recrear el modelo en cada pulsación sería otra opción, pero implicaría reconstruir filas y restablecer la selección innecesariamente.

## Verificación

Pruebas del panel en el hilo de eventos de Swing: carga de los seis datos, búsqueda parcial, cambio de mayúsculas, nombre completo, borrado de la consulta, caracteres especiales, cero coincidencias y conservación del mapa. Se verificará la compilación y, si el entorno gráfico lo permite, la ventana modal y su presentación.
