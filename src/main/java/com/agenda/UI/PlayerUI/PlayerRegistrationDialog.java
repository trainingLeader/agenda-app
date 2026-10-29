package com.agenda.UI.PlayerUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PlayerRegistrationDialog extends JDialog {
    public PlayerRegistrationDialog(Window owner) {
        super(owner, "Registro de jugador", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(new PlayerFormPanel(player -> dispose(), this::dispose));
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelRegistration");
        getRootPane().getActionMap().put("cancelRegistration", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
