package com.agenda.UI.PlayerUI;

import com.agenda.soccer.Player;
import com.agenda.soccer.PlayerRegistry;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PlayerFormPanel extends JPanel {
    private final JTextField nameField = new JTextField(24);
    private final JTextField lastNameField = new JTextField(24);
    private final JTextField ageField = new JTextField(24);
    private final JComboBox<String> positionField = new JComboBox<>(new String[] {
            "Portero",
            "Defensa central",
            "Lateral derecho",
            "Lateral izquierdo",
            "Carrilero derecho",
            "Carrilero izquierdo",
            "Mediocentro defensivo",
            "Mediocentro",
            "Mediocentro ofensivo",
            "Interior derecho",
            "Interior izquierdo",
            "Extremo derecho",
            "Extremo izquierdo",
            "Segundo delantero",
            "Delantero centro"
    });
    private final JTextField countryField = new JTextField(24);
    private final JTextField numberField = new JTextField(24);
    private final JLabel errorLabel = new JLabel(" ");
    private final JButton saveButton = new JButton("Guardar");

    public PlayerFormPanel(Consumer<Player> onSaved, Runnable onCancel) {
        super(new BorderLayout(0, 16));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel heading = new JPanel(new BorderLayout(0, 6));
        JLabel title = new JLabel("Registrar jugador");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        heading.add(title, BorderLayout.NORTH);
        heading.add(new JLabel("Completa todos los campos de la ficha del jugador."), BorderLayout.SOUTH);
        add(heading, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridBagLayout());
        positionField.setSelectedIndex(-1);
        positionField.setToolTipText("Selecciona la posición de juego del jugador.");
        addField(fields, "Nombre", nameField, 0);
        addField(fields, "Apellido", lastNameField, 1);
        addField(fields, "Edad", ageField, 2);
        addField(fields, "Posición", positionField, 3);
        addField(fields, "País", countryField, 4);
        addField(fields, "Número de camiseta", numberField, 5);
        add(fields, BorderLayout.CENTER);

        errorLabel.setName("registrationError");
        errorLabel.setForeground(new Color(166, 32, 32));
        errorLabel.setPreferredSize(new Dimension(440, 40));
        errorLabel.setVerticalAlignment(SwingConstants.TOP);
        errorLabel.getAccessibleContext().setAccessibleName("Error de registro");

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(event -> onCancel.run());
        saveButton.addActionListener(event -> save(onSaved));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.add(cancelButton);
        actions.add(saveButton);

        JPanel footer = new JPanel(new BorderLayout(0, 8));
        footer.add(errorLabel, BorderLayout.NORTH);
        footer.add(actions, BorderLayout.SOUTH);
        add(footer, BorderLayout.SOUTH);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        getRootPane().setDefaultButton(saveButton);
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }

    private void addField(JPanel panel, String text, JComponent field, int row) {
        JLabel label = new JLabel(text);
        label.setLabelFor(field);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(6, 0, 6, 16);
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(6, 0, 6, 0);
        panel.add(field, constraints);
    }

    private void save(Consumer<Player> onSaved) {
        Player player;
        try {
            player = PlayerRegistry.register(nameField.getText(), lastNameField.getText(),
                    ageField.getText(), (String) positionField.getSelectedItem(), countryField.getText(), numberField.getText());
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        }
        errorLabel.setText(" ");
        onSaved.accept(player);
    }
}
