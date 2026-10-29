# Agenda App

Aplicación Java de escritorio en desarrollo para gestionar equipos de fútbol, jugadores y personal. La interfaz utiliza diálogos de Swing (`JOptionPane`). El proyecto también incluye una clase de agenda de contactos por consola y un ejemplo independiente de colecciones.

## Tecnologías y requisitos

- JDK 17 o superior, con soporte para Swing.
- Apache Maven.
- Entorno gráfico disponible para ejecutar la interfaz.

El archivo `pom.xml` configura la compilación para Java 17. La aplicación no requiere dependencias externas en ejecución; utiliza JUnit 4 para las pruebas.

## Compilación y ejecución

Desde la raíz del proyecto, verifica las herramientas instaladas:

```bash
java -version
mvn -version
```

Compila el código:

```bash
mvn compile
```

Inicia la aplicación:

```bash
java -cp target/classes com.agenda.Main
```

También puedes ejecutar el método `main` de `src/main/java/com/agenda/Main.java` desde tu IDE.

Para generar el JAR:

```bash
mvn package
java -cp target/agenda-app-1.0-SNAPSHOT.jar com.agenda.Main
```

El proyecto no configura una clase principal en el manifiesto del JAR, por lo que los comandos anteriores indican explícitamente la clase de entrada.

## Estructura del proyecto

```text
agenda-app/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/agenda/
    │   │   ├── Main.java
    │   │   ├── Colleciones.java
    │   │   ├── UI/
    │   │   │   ├── MainMenu.java
    │   │   │   ├── TeamMenu.java
    │   │   │   ├── PlayerMenu.java
    │   │   │   ├── PlayerFormPanel.java
    │   │   │   ├── PlayerRegistrationDialog.java
    │   │   │   ├── PlayerSearchPanel.java
    │   │   │   ├── PlayerSearchDialog.java
    │   │   │   ├── PlayerDeletionDialog.java
    │   │   │   └── StaffMenu.java
    │   │   ├── soccer/
    │   │   │   ├── Federation.java
    │   │   │   ├── Team.java
    │   │   │   ├── Player.java
    │   │   │   ├── PlayerRegistry.java
    │   │   │   └── Staff.java
    │   │   └── contacto/
    │   │       └── Person.java
    │   └── resources/
    └── test/java/
```

`target/` contiene los archivos generados por Maven. `resources/` está vacío; `test/java/` contiene las pruebas de registro y del formulario.

## Componentes y responsabilidades

| Componente | Responsabilidad actual |
| --- | --- |
| `Main` | Inicia la aplicación y abre el menú principal. |
| `MainMenu` | Permite acceder a los menús de equipos, jugadores y personal, o salir. Intenta aplicar la apariencia Nimbus. |
| `TeamMenu` | Muestra las opciones de agregar, eliminar, buscar y listar equipos. |
| `PlayerMenu` | Muestra las opciones de agregar, eliminar y buscar jugadores. |
| `PlayerFormPanel` | Recoge los datos del jugador y muestra los errores de validación. |
| `PlayerRegistrationDialog` | Aloja el formulario en una ventana modal centrada. |
| `PlayerSearchPanel` | Muestra la tabla de jugadores y filtra por nombre o apellido mientras se escribe. |
| `PlayerSearchDialog` | Aloja el buscador en una ventana modal centrada. |
| `PlayerDeletionDialog` | Permite buscar, seleccionar y confirmar la eliminación de un jugador. |
| `PlayerRegistry` | Valida el registro y guarda los jugadores en el `HashMap` global `PLAYERS`. |
| `StaffMenu` | Muestra las opciones de agregar, eliminar, buscar y mostrar personal. |
| `Federation` | Almacena nombre, apellido y edad; es la clase base de `Player` y `Staff`. |
| `Player` | Agrega posición, país y número a los datos heredados. |
| `Staff` | Agrega el rol a los datos heredados. |
| `Team` | Almacena nombre, país, año de fundación y referencias a mapas de jugadores y personal. |
| `Person` | Almacena datos de contacto e incluye métodos por consola para agregar, buscar y eliminar personas de una lista recibida. |
| `Colleciones` | Ejecuta un ejemplo independiente con `HashMap`, `List` y `computeIfAbsent`. |

### Relaciones del modelo

- `Player` y `Staff` heredan de `Federation`.
- `Team` declara un `Map<String, Player>` y un `Map<String, Staff>`.
- `Person` es independiente de la jerarquía de fútbol.

## Uso y estado actual

Al iniciar la aplicación se muestra este menú:

```text
1. Create Team
2. Create Player
3. Create Staff
0. Exit
```

Las opciones 1, 2 y 3 abren sus respectivos submenús. En cada submenú, la opción `0` regresa al menú principal; en el menú principal, `0` termina la aplicación.

Las opciones `1. Add Player`, `2. Remove Player` y `3. Search Player` permiten registrar, eliminar y buscar jugadores. Las operaciones de equipos y personal siguen pendientes y se procesan como opciones inválidas.

### Registrar un jugador

1. Selecciona `2. Create Player` en el menú principal.
2. Selecciona `1. Add Player`.
3. Completa nombre, apellido, edad, país y número de camiseta, y selecciona la posición en la lista desplegable.
4. Pulsa **Guardar** para registrar el jugador y regresar al menú de jugadores.

Todos los campos son obligatorios. La edad debe ser un entero mayor que cero y el número de camiseta un entero no negativo. Los números de camiseta no pueden repetirse. Si hay errores, el formulario permanece abierto y conserva lo escrito.

La lista de posiciones incluye portero, defensa central, laterales y carrileros derechos e izquierdos, mediocentro defensivo, mediocentro, mediocentro ofensivo, interiores y extremos derechos e izquierdos, segundo delantero y delantero centro. Inicialmente no hay ninguna posición seleccionada y es obligatorio elegir una.

La ventana está centrada y es modal: impide utilizar otras ventanas de la aplicación hasta cerrarla. **Cancelar**, la tecla **Esc** o el botón de cierre descartan el registro.

Los jugadores se guardan en `PlayerRegistry.PLAYERS`, un `HashMap<Integer, Player>` global cuya clave es el número de camiseta. El mapa se comparte entre aperturas del formulario durante la misma ejecución; los datos se pierden al cerrar la aplicación. No se asignan automáticamente a un equipo.

Para consultar un jugador desde el código:

```java
import com.agenda.soccer.Player;
import com.agenda.soccer.PlayerRegistry;

// Devuelve null si no hay un jugador registrado con ese número.
Player player = PlayerRegistry.PLAYERS.get(10);
```

### Buscar jugadores

1. Selecciona `2. Create Player` en el menú principal.
2. Selecciona `3. Search Player`.
3. Escribe en **Nombre o apellido** para filtrar la tabla al instante.

Inicialmente se muestran todos los jugadores del mapa global. La tabla presenta nombre, apellido, edad, posición, país y camiseta. La búsqueda acepta coincidencias parciales y el nombre completo, sin distinguir mayúsculas de minúsculas; ignora los espacios al inicio y al final de la consulta y trata los caracteres especiales como texto literal. Las tildes sí se distinguen.

Al borrar el texto vuelven a aparecer todos los jugadores. El contador indica cuántas filas coinciden respecto del total; si no hay coincidencias o registros, la tabla queda vacía. Puedes ordenar por cualquier columna pulsando su encabezado; edad y camiseta se ordenan numéricamente.

La tabla es de consulta y no permite modificar datos. Cada apertura carga los jugadores registrados hasta ese momento. El diálogo es modal y centrado; puedes cerrarlo con **Cerrar**, **Esc** o el botón de cierre de la ventana.

### Eliminar un jugador

1. Selecciona `2. Create Player` en el menú principal.
2. Selecciona `2. Remove Player` en el menú de jugadores.
3. Busca por nombre o apellido y selecciona una fila de la tabla.
4. Revisa el nombre y la camiseta en la confirmación y elige **Sí** para eliminar al jugador.

La confirmación selecciona **No** de forma predeterminada. Elegir **No** o cerrar la confirmación conserva al jugador y permite volver a seleccionarlo.

Al confirmar, el jugador se elimina del `HashMap` global y de la tabla. El diálogo permanece abierto, conserva el filtro y la ordenación, y actualiza el contador. Cerrar el diálogo o pulsar **Esc** no elimina otros jugadores. La pantalla de búsqueda de la opción 3 sigue siendo de consulta.

### Funcionalidades disponibles

- Navegación entre el menú principal y los submenús.
- Registro de jugadores mediante un formulario modal con validación y almacenamiento global en memoria.
- Búsqueda de jugadores con filtrado inmediato, tabla de consulta y contador de resultados.
- Eliminación de jugadores seleccionados con confirmación y actualización inmediata del mapa y la tabla.
- Mensajes de error para entradas no numéricas y opciones no reconocidas.
- Modelos con constructores, getters y setters.
- Métodos de gestión de contactos en `Person`, sin integración con la interfaz Swing ni punto de entrada propio.
- Ejemplo de colecciones ejecutable por separado:

  ```bash
  java -cp target/classes com.agenda.Colleciones
  ```

  Salida esperada: `[Java, Spring]`.

### Limitaciones conocidas

- No existe persistencia en archivos ni base de datos.
- Los mapas de jugadores y personal de `Team` no se inicializan en su constructor; permanecen en `null` hasta asignarlos mediante sus setters.
- Cancelar o cerrar los diálogos del menú principal, equipos o personal puede producir `NullPointerException`. El menú de jugadores ya maneja la cancelación.
- Los modelos no validan directamente sus setters ni sus constructores. El registro de jugadores valida los datos a través de `PlayerRegistry.register`.
- El mapa global es mutable: se recomienda usar `PlayerRegistry.register` para registrar jugadores con validación y acceder a él desde el hilo de eventos de Swing.
- Los métodos de consola de `Person` requieren que `System.console()` esté disponible; puede devolver `null` al ejecutar desde un IDE.
- La sobrecarga `Person.removePerson(List<Person>, String)` compara por apellido y modifica la lista dentro de un `forEach`, lo que puede producir `ConcurrentModificationException`.
- El título del diálogo de `TeamMenu` muestra actualmente `Menu Staff`.

## Pruebas

Las pruebas JUnit verifican el registro, la validación, el rechazo de duplicados, las acciones de guardar y cancelar, la carga y filtrado de la tabla, y el borrado confirmado o cancelado con filtros y ordenación activos. Para ejecutarlas:

```bash
mvn test
```

Los controles del panel pueden probarse sin pantalla gráfica:

```bash
mvn -Djava.awt.headless=true test
```

Para comprobar manualmente el centrado y la modalidad, ejecuta la aplicación y abre el formulario siguiendo los pasos anteriores. No debe reaparecer el menú de jugadores mientras el formulario esté abierto.

## Próximos pasos sugeridos

1. Inicializar las colecciones de `Team` y definir las claves de sus mapas.
2. Implementar las operaciones pendientes de gestión y conectarlas con los menús.
3. Separar la entrada de datos, la lógica de gestión y el almacenamiento.
4. Manejar la cancelación de diálogos y validar los datos ingresados.
5. Definir la integración de la agenda de contactos con la gestión de fútbol.
6. Ampliar las pruebas e incorporar persistencia según las necesidades del proyecto.
