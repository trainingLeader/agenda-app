# Plan de implementación del registro de jugadores

**Objetivo:** abrir un formulario modal desde PlayerMenu y registrar los jugadores en un HashMap global.

**Arquitectura:** PlayerRegistry valida y registra; PlayerFormPanel presenta los seis campos y las acciones; PlayerRegistrationDialog configura la modalidad y el centrado. Se mantiene el paquete UI existente y se utiliza Swing en su hilo de eventos.

**Tecnologías:** Java 17, Swing, Maven y JUnit 4 para pruebas.

## Pasos

- [x] Añadir JUnit con alcance test y pruebas de registro: normalización, campos obligatorios, edad positiva, dorsal no negativo, valores enteros y rechazo de duplicados sin sobrescribir.
- [x] Ejecutar las pruebas antes de implementar y comprobar el fallo por la ausencia de PlayerRegistry.
- [x] Crear `soccer/PlayerRegistry.java` con `public static final HashMap<Integer, Player> PLAYERS` y `public static Player register(String name, String lastName, String age, String position, String country, String number)`. Los errores se expresan mediante IllegalArgumentException y no modifican el mapa.
- [x] Probar el formulario con datos válidos, inválidos y cancelación mediante sus controles Swing en el hilo de eventos.
- [x] Crear `UI/PlayerFormPanel.java` con campos etiquetados, mensajes de error visibles y callbacks para guardar y cancelar. Crear `UI/PlayerRegistrationDialog.java` con APPLICATION_MODAL, DISPOSE_ON_CLOSE, pack y setLocationRelativeTo(null).
- [x] Conectar la opción 1 de PlayerMenu, manejar su cancelación e iniciar MainMenu mediante SwingUtilities.invokeLater desde Main.
- [x] Actualizar README y ejecutar `mvn -o test`, revisar diferencias y verificar la presentación del panel si el entorno lo permite.

Se trabajará en el directorio actual para conservar los cambios del usuario en PlayerMenu; no se crearán commits ni se modificarán otras funcionalidades.

## Resultado de verificación

- `mvn -o -Djava.awt.headless=true test`: 9 pruebas, 0 fallos y 0 errores.
- Comprobación gráfica con Nimbus: diálogo centrado respecto al área visible de macOS, modalidad APPLICATION_MODAL, bloqueo de setVisible hasta cancelar y mapa vacío tras cancelar.
- Presentación del panel revisada mediante captura renderizada.
- `git diff --check`: sin errores.
