# Diseño del registro de jugadores

## Alcance

La opción `1. Add Player` de `PlayerMenu` abrirá un formulario de registro. `Player.java` contiene el modelo y no tiene opciones de menú; la integración corresponde a `PlayerMenu.java`.

## Ventana

Se utilizará un `JDialog` con modalidad `APPLICATION_MODAL` y un `JPanel` como contenido. Un `JFrame` no ofrece modalidad nativa, y un `JPanel` por sí solo no es una ventana. El diálogo se ajustará a su contenido y se centrará en la pantalla antes de mostrarse.

Mientras el diálogo esté abierto, la aplicación no permitirá interactuar con otros menús. Se cerrará al guardar correctamente, pulsar Cancelar o usar el botón de cierre de la ventana.

## Formulario y datos

- Campos: nombre, apellido, edad, posición, país y número de camiseta.
- Posición se selecciona en una lista desplegable no editable con posiciones de fútbol; inicialmente no hay selección y se exige elegir una al guardar.
- Botones: Guardar y Cancelar.
- Todos los campos serán obligatorios; los textos se recortarán para quitar espacios al principio y al final.
- Edad: entero mayor que cero. Número de camiseta: entero mayor o igual que cero.
- Los errores se mostrarán dentro del contexto del diálogo y conservarán los datos para corregirlos.
- Guardar creará un `Player` y lo añadirá al `HashMap<Integer, Player>` global `PlayerRegistry.PLAYERS`, usando el número de camiseta como clave y rechazando duplicados. No se incorporará persistencia en disco ni asociación a equipos.
- Cancelar o cerrar no registrará ningún jugador.

## Integración y verificación

Se conservarán los cambios existentes en `PlayerMenu`, incluida la opción 1 ya declarada. La interfaz nueva se ejecutará en el hilo de eventos de Swing. Se manejará la cancelación del menú de jugadores para evitar invocar `trim()` sobre `null`.

Se verificará la compilación y se comprobarán el registro con datos válidos, el rechazo de datos incompletos o números inválidos, la cancelación, el centrado y el bloqueo modal. Las verificaciones que requieran un entorno gráfico se distinguirán de las comprobaciones automatizadas.
