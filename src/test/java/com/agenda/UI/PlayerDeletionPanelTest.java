package com.agenda.UI;

import com.agenda.UI.PlayerUI.PlayerSearchPanel;
import com.agenda.soccer.Player;
import com.agenda.soccer.PlayerRegistry;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class PlayerDeletionPanelTest {
    @Before
    public void preparePlayers() {
        PlayerRegistry.PLAYERS.clear();
        PlayerRegistry.register("Ana", "Torres", "22", "Portero", "Colombia", "1");
        PlayerRegistry.register("Luis", "Paz", "30", "Defensa central", "Perú", "10");
        PlayerRegistry.register("Ana María", "Ríos", "24", "Delantero centro", "México", "2");
    }

    @Test
    public void confirmationDeletesCorrectFilteredAndSortedPlayerAndKeepsFilter() throws Exception {
        AtomicReference<PlayerSearchPanel> panelRef = new AtomicReference<>();
        AtomicReference<Player> confirmed = new AtomicReference<>();
        AtomicInteger prompts = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {}, player -> {
                prompts.incrementAndGet();
                confirmed.set(player);
                return true;
            });
            panelRef.set(panel);
            query(panel).setText("ana");
            table(panel).getRowSorter().setSortKeys(List.of(new RowSorter.SortKey(5, SortOrder.DESCENDING)));
            table(panel).setRowSelectionInterval(0, 0);
        });
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = panelRef.get();
            assertEquals(2, confirmed.get().getNumber());
            assertFalse(PlayerRegistry.PLAYERS.containsKey(2));
            assertTrue(PlayerRegistry.PLAYERS.containsKey(1));
            assertTrue(PlayerRegistry.PLAYERS.containsKey(10));
            assertEquals(1, table(panel).getRowCount());
            assertEquals("Torres", table(panel).getValueAt(0, 1));
            assertEquals("ana", query(panel).getText());
            assertEquals("1 de 2 jugadores", find(panel, JLabel.class, "searchResults").getText());
            assertEquals(SortOrder.DESCENDING, table(panel).getRowSorter().getSortKeys().get(0).getSortOrder());
            assertEquals(-1, table(panel).getSelectedRow());
        });
        SwingUtilities.invokeAndWait(() -> assertEquals(1, prompts.get()));
    }

    @Test
    public void decliningKeepsPlayerAndAllowsSelectingAgain() throws Exception {
        AtomicReference<PlayerSearchPanel> panelRef = new AtomicReference<>();
        AtomicInteger prompts = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {}, player -> {
                prompts.incrementAndGet();
                return false;
            });
            panelRef.set(panel);
            table(panel).setRowSelectionInterval(0, 0);
        });
        SwingUtilities.invokeAndWait(() -> {
            assertEquals(3, PlayerRegistry.PLAYERS.size());
            assertEquals(3, table(panelRef.get()).getRowCount());
            assertEquals(-1, table(panelRef.get()).getSelectedRow());
            table(panelRef.get()).setRowSelectionInterval(0, 0);
        });
        SwingUtilities.invokeAndWait(() -> {
            assertEquals(2, prompts.get());
            assertEquals(3, PlayerRegistry.PLAYERS.size());
        });
    }

    @Test
    public void deletingLastPlayerLeavesEmptyTableWithoutAnotherConfirmation() throws Exception {
        AtomicReference<PlayerSearchPanel> panelRef = new AtomicReference<>();
        AtomicInteger prompts = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            PlayerRegistry.PLAYERS.remove(2);
            PlayerRegistry.PLAYERS.remove(10);
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {}, player -> {
                prompts.incrementAndGet();
                return true;
            });
            panelRef.set(panel);
            table(panel).setRowSelectionInterval(0, 0);
        });
        SwingUtilities.invokeAndWait(() -> {
            assertTrue(PlayerRegistry.PLAYERS.isEmpty());
            assertEquals(0, table(panelRef.get()).getRowCount());
            assertEquals("0 de 0 jugadores", find(panelRef.get(), JLabel.class, "searchResults").getText());
            assertEquals(1, prompts.get());
        });
    }

    @Test
    public void changingSearchCancelsPendingSelectionWithoutDeleting() throws Exception {
        AtomicInteger prompts = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {}, player -> {
                prompts.incrementAndGet();
                return true;
            });
            table(panel).setRowSelectionInterval(0, 0);
            query(panel).setText("Luis");
            assertEquals(1, table(panel).getRowCount());
        });
        SwingUtilities.invokeAndWait(() -> {
            assertEquals(0, prompts.get());
            assertEquals(3, PlayerRegistry.PLAYERS.size());
        });
    }

    @Test
    public void searchingAndSortingWithoutSelectionDoNotAskForDeletion() throws Exception {
        AtomicInteger prompts = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {}, player -> {
                prompts.incrementAndGet();
                return true;
            });
            query(panel).setText("No existe");
            assertEquals(0, table(panel).getRowCount());
            query(panel).setText("");
            table(panel).getRowSorter().toggleSortOrder(0);
        });
        SwingUtilities.invokeAndWait(() -> {
            assertEquals(0, prompts.get());
            assertEquals(3, PlayerRegistry.PLAYERS.size());
        });
    }

    @Test
    public void queryModeSelectionDoesNotDelete() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PlayerSearchPanel panel = new PlayerSearchPanel(() -> {});
            table(panel).setRowSelectionInterval(0, 0);
        });
        SwingUtilities.invokeAndWait(() -> assertEquals(3, PlayerRegistry.PLAYERS.size()));
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
