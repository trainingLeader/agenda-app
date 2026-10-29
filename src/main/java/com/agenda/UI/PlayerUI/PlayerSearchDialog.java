package com.agenda.UI.PlayerUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PlayerSearchDialog extends JDialog {
    public PlayerSearchDialog(Window owner) {
        super(owner, "Buscador de jugadores", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(new PlayerSearchPanel(this::dispose));
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeSearch");
        getRootPane().getActionMap().put("closeSearch", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        pack();
        setMinimumSize(new Dimension(660, 360));
        setLocationRelativeTo(null);
    }
}
