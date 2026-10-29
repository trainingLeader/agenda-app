package com.agenda.UI.PlayerUI;

import com.agenda.soccer.Player;
import com.agenda.soccer.PlayerRegistry;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class PlayerSearchPanel extends JPanel {
    private final JTextField searchField = new JTextField(28);
    private final JLabel resultsLabel = new JLabel();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[] {"Nombre", "Apellido", "Edad", "Posición", "País", "Camiseta"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return column == 2 || column == 5 ? Integer.class : String.class;
        }
    };
    private final JTable table = new JTable(model);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    private boolean updatingSelection;
    private boolean handlingDeletion;

    public PlayerSearchPanel(Runnable onClose) {
        this(onClose, null);
    }

    public PlayerSearchPanel(Runnable onClose, Predicate<Player> confirmDeletion) {
        super(new BorderLayout(0, 16));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel(confirmDeletion == null ? "Buscar jugadores" : "Eliminar jugadores");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        JPanel heading = new JPanel(new BorderLayout(0, 6));
        heading.add(title, BorderLayout.NORTH);
        heading.add(new JLabel(confirmDeletion == null
                ? "Escribe un nombre o apellido para filtrar la lista."
                : "Busca y selecciona un jugador para confirmar su eliminación."), BorderLayout.SOUTH);

        JLabel searchLabel = new JLabel("Nombre o apellido");
        searchLabel.setLabelFor(searchField);
        searchField.setName("playerSearch");
        JPanel searchBar = new JPanel(new BorderLayout(12, 0));
        searchBar.add(searchLabel, BorderLayout.WEST);
        searchBar.add(searchField, BorderLayout.CENTER);
        JPanel header = new JPanel(new BorderLayout(0, 16));
        header.add(heading, BorderLayout.NORTH);
        header.add(searchBar, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        table.setRowSorter(sorter);
        for (Player player : PlayerRegistry.PLAYERS.values()) {
            model.addRow(new Object[] {player.getName(), player.getLastName(), player.getAge(),
                    player.getPosition(), player.getCountry(), player.getNumber()});
        }
        table.setName("playersTable");
        table.getAccessibleContext().setAccessibleName("Jugadores registrados");
        sorter.setSortKeys(List.of(new RowSorter.SortKey(5, SortOrder.ASCENDING)));
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(760, 300));
        add(scrollPane, BorderLayout.CENTER);

        resultsLabel.setName("searchResults");
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(event -> onClose.run());
        JPanel footer = new JPanel(new BorderLayout(12, 0));
        footer.add(resultsLabel, BorderLayout.WEST);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.add(closeButton);
        footer.add(actions, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                filterPlayers();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                filterPlayers();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                filterPlayers();
            }
        });
        filterPlayers();
        if (confirmDeletion != null) {
            table.getSelectionModel().addListSelectionListener(event -> {
                if (!event.getValueIsAdjusting() && !updatingSelection && !handlingDeletion
                        && table.getSelectedRow() >= 0) {
                    requestDeletion(confirmDeletion);
                }
            });
        }
        SwingUtilities.invokeLater(() -> searchField.requestFocusInWindow());
    }

    private void filterPlayers() {
        updatingSelection = true;
        try {
            table.clearSelection();
            applyFilter();
        } finally {
            updatingSelection = false;
        }
    }

    private void applyFilter() {
        String query = searchField.getText().trim().toLowerCase(Locale.ROOT);
        sorter.setRowFilter(query.isEmpty() ? null : new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                String fullName = entry.getStringValue(0) + " " + entry.getStringValue(1);
                return fullName.toLowerCase(Locale.ROOT).contains(query);
            }
        });
        updateResults();
    }

    private void updateResults() {
        resultsLabel.setText(table.getRowCount() + " de " + model.getRowCount() + " jugadores");
    }

    private void requestDeletion(Predicate<Player> confirmDeletion) {
        handlingDeletion = true;
        // Esperar a que termine el evento de selección antes de modificar las filas.
        SwingUtilities.invokeLater(() -> {
            try {
                int viewRow = table.getSelectedRow();
                if (viewRow < 0) {
                    return;
                }
                int modelRow = table.convertRowIndexToModel(viewRow);
                int number = (Integer) model.getValueAt(modelRow, 5);
                Player player = PlayerRegistry.PLAYERS.get(number);
                if (player != null && confirmDeletion.test(player)
                        && PlayerRegistry.PLAYERS.remove(number, player)) {
                    table.clearSelection();
                    model.removeRow(modelRow);
                    updateResults();
                }
            } finally {
                table.clearSelection();
                handlingDeletion = false;
            }
        });
    }
}
