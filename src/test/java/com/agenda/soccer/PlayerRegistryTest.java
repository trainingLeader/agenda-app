package com.agenda.soccer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerRegistryTest {
    @Before
    public void clearPlayers() {
        PlayerRegistry.PLAYERS.clear();
    }

    @Test
    public void registersAllFieldsAndTrimsText() {
        Player player = PlayerRegistry.register(" Ana ", " Torres ", " 22 ", " Portera ", " Colombia ", " 1 ");
        assertSame(player, PlayerRegistry.PLAYERS.get(1));
        assertEquals(1, PlayerRegistry.PLAYERS.size());
        assertEquals("Ana", player.getName());
        assertEquals("Torres", player.getLastName());
        assertEquals(22, player.getAge());
        assertEquals("Portera", player.getPosition());
        assertEquals("Colombia", player.getCountry());
        assertEquals(1, player.getNumber());
    }

    @Test
    public void rejectsDuplicateWithoutReplacingOriginal() {
        Player original = PlayerRegistry.register("Ana", "Torres", "22", "Portera", "Colombia", "1");
        assertThrows(IllegalArgumentException.class,
                () -> PlayerRegistry.register("Luis", "Paz", "25", "Defensa", "Perú", "1"));
        assertSame(original, PlayerRegistry.PLAYERS.get(1));
        assertEquals(1, PlayerRegistry.PLAYERS.size());
    }

    @Test
    public void rejectsEveryMissingFieldWithoutSaving() {
        for (int index = 0; index < 6; index++) {
            for (String missing : new String[] {null, "", "   "}) {
                String[] fields = {"Ana", "Torres", "22", "Portera", "Colombia", "1"};
                fields[index] = missing;
                assertThrows(IllegalArgumentException.class, () -> register(fields));
                assertTrue(PlayerRegistry.PLAYERS.isEmpty());
            }
        }
    }

    @Test
    public void rejectsInvalidAgeWithoutSaving() {
        for (String age : new String[] {"0", "-1", "abc", "22.5", "2147483648"}) {
            assertThrows(IllegalArgumentException.class,
                    () -> PlayerRegistry.register("Ana", "Torres", age, "Portera", "Colombia", "1"));
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
        }
    }

    @Test
    public void rejectsInvalidNumberWithoutSaving() {
        for (String number : new String[] {"-1", "abc", "1.5", "2147483648"}) {
            assertThrows(IllegalArgumentException.class,
                    () -> PlayerRegistry.register("Ana", "Torres", "22", "Portera", "Colombia", number));
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
        }
    }

    @Test
    public void acceptsZeroAsShirtNumber() {
        PlayerRegistry.register("Ana", "Torres", "22", "Portera", "Colombia", "0");
        assertTrue(PlayerRegistry.PLAYERS.containsKey(0));
    }

    private void register(String[] fields) {
        PlayerRegistry.register(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
    }
}
