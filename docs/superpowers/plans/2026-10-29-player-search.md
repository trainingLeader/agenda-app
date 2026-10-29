# Plan de implementación del buscador de jugadores

**Objetivo:** mostrar y filtrar los jugadores del mapa global desde la opción 3 de PlayerMenu.

**Arquitectura:** PlayerSearchPanel carga una instantánea de PlayerRegistry.PLAYERS en una JTable de consulta. Un DocumentListener actualiza un filtro literal de TableRowSorter sobre nombre y apellido. PlayerSearchDialog configura la modalidad, el centrado y las acciones de cierre.

**Tecnologías:** Java 17, Swing y las pruebas JUnit 4 existentes. Sin nuevas dependencias.

## Pasos

- [x] Crear `src/test/java/com/agenda/UI/PlayerSearchPanelTest.java`: comprobar seis columnas, consulta parcial y por nombre completo, mayúsculas, espacios externos, caracteres literales, borrado del filtro, ausencia de datos o coincidencias, ordenación numérica y mapa sin modificaciones.
- [x] Ejecutar `mvn -o -Djava.awt.headless=true test` y observar el fallo por ausencia de PlayerSearchPanel.
- [x] Crear `UI/PlayerSearchPanel.java` con modelo no editable, columnas numéricas para edad y camiseta, carga inicial del mapa, filtro sobre nombre completo y contador de filas visibles.
- [x] Crear `UI/PlayerSearchDialog.java` con APPLICATION_MODAL, DISPOSE_ON_CLOSE, botón Cerrar y Esc. Usar pack y setLocationRelativeTo(null).
- [x] Conectar `case 3` en `UI/PlayerMenu.java` y actualizar README con instrucciones de uso y alcance de la búsqueda.
- [x] Ejecutar todas las pruebas, comprobar el diálogo en un entorno gráfico y revisar `git diff --check`.

Se preservarán los cambios existentes y se trabajará en el directorio actual, sin commits ni cambios de rama.

## Verificación

- `mvn -o -Djava.awt.headless=true test`: 18 pruebas, 0 fallos y 0 errores.
- Comprobación gráfica con Nimbus: ventana centrada y modal, filtrado inmediato de 3 a 2 filas al escribir, cierre correcto y mapa sin modificaciones.
- Presentación de la tabla y el contador revisada mediante captura renderizada.
- `git diff --check`: sin errores.
