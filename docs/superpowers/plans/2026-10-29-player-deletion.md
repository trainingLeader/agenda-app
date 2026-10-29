# Plan de implementación del borrado de jugadores

**Objetivo:** eliminar un jugador del HashMap y de la tabla únicamente tras confirmar su selección.

**Arquitectura:** PlayerSearchPanel conservará su constructor de consulta y añadirá uno con Predicate<Player> para solicitar confirmación. Se convertirá el índice visible al índice del modelo y se procesará la selección en el siguiente evento de Swing para actualizar las filas sin reentradas. PlayerDeletionDialog mostrará la confirmación real con No como opción inicial.

**Tecnologías:** Java 17, Swing, JUnit 4. Sin nuevas dependencias.

## Pasos

- [x] Crear PlayerDeletionPanelTest con casos de confirmación, cancelación, selección filtrada y ordenada, eliminación del último registro, cancelación de selección por filtrado, cero resultados y regresión del modo consulta.
- [x] Ejecutar las pruebas y comprobar que falla el constructor nuevo todavía ausente.
- [x] Ampliar PlayerSearchPanel con confirmación opcional, título de eliminación, protección frente a eventos encadenados, borrado por clave y actualización del contador sin perder filtro ni orden.
- [x] Crear PlayerDeletionDialog modal y centrado, confirmación Sí/No identificando nombre y camiseta, cierre con Cerrar o Esc, e integrar case 2 en PlayerMenu.
- [x] Actualizar README, ejecutar todas las pruebas, verificar el diálogo gráfico y revisar git diff --check.

Se preservarán los cambios existentes; no se crearán commits ni se cambiará de rama.

## Verificación

- `mvn -o -Djava.awt.headless=true test`: 24 pruebas, 0 fallos y 0 errores.
- Las pruebas comprobaron y permitieron corregir la conexión del ordenador de filas antes de cargar datos, necesaria para procesar correctamente las eliminaciones.
- Comprobación gráfica con Nimbus: ventana centrada y modal, confirmación con No inicial, cancelación sin cambios, borrado del jugador correcto con filtro y ordenación, tabla actualizada y filtro conservado.
- Capturas del diálogo de confirmación y del resultado revisadas.
- `git diff --check`: sin errores.
