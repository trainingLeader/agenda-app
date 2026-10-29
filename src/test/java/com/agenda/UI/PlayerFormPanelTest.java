package com.agenda.UI;

import com.agenda.UI.PlayerUI.PlayerFormPanel;
import com.agenda.soccer.Player;
import com.agenda.soccer.PlayerRegistry;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class PlayerFormPanelTest {
    @Before
    public void clearPlayers() {
        PlayerRegistry.PLAYERS.clear();
    }

    @Test
    public void saveRegistersFormValuesAndNotifiesDialog() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AtomicReference<Player> saved = new AtomicReference<>();
            PlayerFormPanel panel = new PlayerFormPanel(saved::set, () -> fail("Unexpected cancellation"));
            fill(panel);
            button(panel, "Guardar").doClick();
            assertNotNull(saved.get());
            assertSame(saved.get(), PlayerRegistry.PLAYERS.get(10));
            assertEquals("Ana", saved.get().getName());
            assertEquals("Torres", saved.get().getLastName());
            assertEquals(22, saved.get().getAge());
            assertEquals("Delantero centro", saved.get().getPosition());
            assertEquals("Colombia", saved.get().getCountry());
        });
    }

    @Test
    public void invalidSaveKeepsFormOpenAndPreservesInput() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AtomicBoolean closed = new AtomicBoolean();
            PlayerFormPanel panel = new PlayerFormPanel(player -> closed.set(true), () -> closed.set(true));
            fill(panel);
            field(panel, "Edad").setText("abc");
            button(panel, "Guardar").doClick();
            assertFalse(closed.get());
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
            assertEquals("Ana", field(panel, "Nombre").getText());
            assertEquals("abc", field(panel, "Edad").getText());
            assertTrue(hasError(panel));
        });
    }

    @Test
    public void missingPositionKeepsFormOpenWithoutSaving() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AtomicBoolean saved = new AtomicBoolean();
            PlayerFormPanel panel = new PlayerFormPanel(player -> saved.set(true), () -> {});
            assertNull(position(panel).getSelectedItem());
            fill(panel);
            position(panel).setSelectedIndex(-1);
            button(panel, "Guardar").doClick();
            assertFalse(saved.get());
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
            assertTrue(hasError(panel));
        });
    }

    @Test
    public void selectedPositionsAreSavedFromTheDropdown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AtomicReference<Player> saved = new AtomicReference<>();
            PlayerFormPanel panel = new PlayerFormPanel(saved::set, () -> {});
            assertFalse(position(panel).isEditable());
            for (String value : new String[] {"Portero", "Defensa central", "Lateral derecho",
                    "Lateral izquierdo", "Mediocentro defensivo", "Mediocentro",
                    "Mediocentro ofensivo", "Extremo derecho", "Extremo izquierdo", "Delantero centro"}) {
                PlayerRegistry.PLAYERS.clear();
                saved.set(null);
                fill(panel);
                position(panel).setSelectedItem(value);
                button(panel, "Guardar").doClick();
                assertNotNull("La posición debe poder seleccionarse: " + value, saved.get());
                assertEquals(value, saved.get().getPosition());
            }
        });
    }

    @Test
    public void cancelClosesWithoutSaving() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AtomicBoolean cancelled = new AtomicBoolean();
            PlayerFormPanel panel = new PlayerFormPanel(player -> fail("Unexpected registration"),
                    () -> cancelled.set(true));
            fill(panel);
            button(panel, "Cancelar").doClick();
            assertTrue(cancelled.get());
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
        });
    }

    private static void fill(Container panel) {
        field(panel, "Nombre").setText("Ana");
        field(panel, "Apellido").setText("Torres");
        field(panel, "Edad").setText("22");
        position(panel).setSelectedItem("Delantero centro");
        field(panel, "País").setText("Colombia");
        field(panel, "Número de camiseta").setText("10");
    }

    private static JTextField field(Container container, String label) {
        return (JTextField) labelledControl(container, label);
    }

    private static JComboBox<?> position(Container container) {
        return (JComboBox<?>) labelledControl(container, "Posición");
    }

    private static Component labelledControl(Container container, String label) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel && label.equals(((JLabel) component).getText())) {
                return ((JLabel) component).getLabelFor();
            }
            if (component instanceof Container) {
                Component found = labelledControl((Container) component, label);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static JButton button(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && text.equals(((JButton) component).getText())) {
                return (JButton) component;
            }
            if (component instanceof Container) {
                JButton found = button((Container) component, text);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static boolean hasError(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel && "registrationError".equals(component.getName())) {
                return !((JLabel) component).getText().trim().isEmpty();
            }
            if (component instanceof Container && hasError((Container) component)) return true;
        }
        return false;
    }
}
