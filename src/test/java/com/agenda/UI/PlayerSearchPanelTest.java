package com.agenda.UI;

import com.agenda.UI.PlayerUI.PlayerSearchPanel;
import com.agenda.soccer.PlayerRegistry;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PlayerSearchPanelTest {
    @Before
    public void preparePlayers() {
        PlayerRegistry.PLAYERS.clear();
        PlayerRegistry.register("Ana", "Torres", "22", "Portero", "Colombia", "1");
        PlayerRegistry.register("Luis", "Paz", "30", "Defensa central", "Perú", "10");
        PlayerRegistry.register("Ana María", "Ríos", "24", "Delantero centro", "México", "2");
    }

    @Test
    public void showsAllPlayerInformationInReadOnlyTable() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            JTable table = table(panel);
            assertEquals(3, table.getRowCount());
            assertEquals(6, table.getColumnCount());
            Object[] first = {"Ana", "Torres", 22, "Portero", "Colombia", 1};
            for (int column = 0; column < first.length; column++) {
                assertEquals(first[column], table.getValueAt(0, column));
                assertFalse(table.isCellEditable(0, column));
            }
        });
    }

    @Test
    public void filtersAsEachCharacterIsTypedAndRestoresRowsWhenCleared() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            JTextField query = query(panel);
            query.setText("a");
            assertEquals(3, table(panel).getRowCount());
            query.setText("an");
            assertEquals(2, table(panel).getRowCount());
            query.setText("ana t");
            assertEquals(1, table(panel).getRowCount());
            assertEquals("Torres", table(panel).getValueAt(0, 1));
            query.setText("");
            assertEquals(3, table(panel).getRowCount());
        });
    }

    @Test
    public void matchesPartialSurnameAndFullNameIgnoringCaseAndOuterSpaces() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            for (String value : Arrays.asList("  tORr  ", "ANA TORRES")) {
                query(panel).setText(value);
                assertEquals(1, table(panel).getRowCount());
                assertEquals("Torres", table(panel).getValueAt(0, 1));
            }
            query(panel).setText("   ");
            assertEquals(3, table(panel).getRowCount());
        });
    }

    @Test
    public void treatsSpecialCharactersLiterally() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerRegistry.register("A.[", "Test", "28", "Portero", "Chile", "99");
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            query(panel).setText(".[");
            assertEquals(1, table(panel).getRowCount());
            assertEquals("A.[", table(panel).getValueAt(0, 0));
        });
    }

    @Test
    public void noMatchesShowsZeroWithoutChangingTheRegistry() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            java.util.Map<Integer, com.agenda.soccer.Player> original = new java.util.HashMap<>(PlayerRegistry.PLAYERS);
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            query(panel).setText("No existe");
            assertEquals(0, table(panel).getRowCount());
            assertEquals("0 de 3 jugadores", find(panel, JLabel.class, "searchResults").getText());
            assertEquals(original, PlayerRegistry.PLAYERS);
        });
    }

    @Test
    public void emptyRegistryShowsEmptyTableAndNewOpeningLoadsRecentPlayers() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerRegistry.PLAYERS.clear();
            PlayerSearchPanel empty = new PlayerSearchPanel(() -> {});
            assertEquals(0, table(empty).getRowCount());
            assertEquals("0 de 0 jugadores", find(empty, JLabel.class, "searchResults").getText());
            PlayerRegistry.register("Nueva", "Jugadora", "20", "Portero", "Colombia", "7");
            PlayerSearchPanel reopened = new PlayerSearchPanel(() -> {});
            assertEquals(1, table(reopened).getRowCount());
            assertEquals("Nueva", table(reopened).getValueAt(0, 0));
        });
    }

    @Test
    public void sortsShirtNumbersNumerically() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            JTable table = table(panel);
            table.getRowSorter().setSortKeys(Arrays.asList(new RowSorter.SortKey(5, SortOrder.ASCENDING)));
            java.util.List<Object> numbers = new ArrayList<>();
            for (int row = 0; row < table.getRowCount(); row++) numbers.add(table.getValueAt(row, 5));
            assertEquals(Arrays.asList(1, 2, 10), numbers);
        });
    }

    private static JTable table(Container panel) {
        return find(panel, JTable.class, "playersTable");
    }

    private static JTextField query(Container panel) {
        return find(panel, JTextField.class, "playerSearch");
    }

    private static <T extends Component> T find(Container container, Class<T> type, String name) {
        for (Component component : container.getComponents()) {
            if (type.isInstance(component) && name.equals(component.getName())) return type.cast(component);
            if (component instanceof Container) {
                T found = find((Container) component, type, name);
                if (found != null) return found;
            }
        }
        return null;
    }
}
