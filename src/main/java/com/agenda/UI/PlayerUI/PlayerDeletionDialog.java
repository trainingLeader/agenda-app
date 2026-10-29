package com.agenda.UI.PlayerUI;

import com.agenda.soccer.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PlayerDeletionDialog extends JDialog {
    public PlayerDeletionDialog(Window owner) {
        super(owner, "Eliminar jugador", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(new PlayerSearchPanel(this::dispose, this::confirmDeletion));
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeDeletion");
        getRootPane().getActionMap().put("closeDeletion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        pack();
        setMinimumSize(new Dimension(660, 360));
        setLocationRelativeTo(null);
    }

    private boolean confirmDeletion(Player player) {
        if (!isShowing()) {
            return false;
        }
        Object[] options = {"Sí", "No"};
        int result = JOptionPane.showOptionDialog(this,
                "¿Deseas eliminar a " + player.getName() + " " + player.getLastName()
                        + " (camiseta " + player.getNumber() + ")?",
                "Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        return result == 0;
    }
}
