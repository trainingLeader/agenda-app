package com.agenda.soccer;

import java.util.HashMap;

/** Registro compartido en memoria durante la ejecución de la aplicación. */
public final class PlayerRegistry {
    public static final HashMap<Integer, Player> PLAYERS = new HashMap<>();

    private PlayerRegistry() {
    }

    public static Player register(String name, String lastName, String age,
                                  String position, String country, String number) {
        name = required(name, "El nombre");
        lastName = required(lastName, "El apellido");
        position = required(position, "La posición");
        country = required(country, "El país");
        int playerAge = integer(age, "La edad");
        int shirtNumber = integer(number, "El número de camiseta");

        if (playerAge <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que cero.");
        }
        if (shirtNumber < 0) {
            throw new IllegalArgumentException("El número de camiseta no puede ser negativo.");
        }
        if (PLAYERS.containsKey(shirtNumber)) {
            throw new IllegalArgumentException("Ya existe un jugador con ese número de camiseta.");
        }

        Player player = new Player(name, lastName, playerAge, position, country, shirtNumber);
        PLAYERS.put(shirtNumber, player);
        return player;
    }

    private static String required(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " es obligatorio.");
        }
        return value.trim();
    }

    private static int integer(String value, String field) {
        String text = required(value, field);
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(field + " debe ser un número entero válido.");
        }
    }
}
