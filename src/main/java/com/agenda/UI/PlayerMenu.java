package com.agenda.UI;

import javax.swing.JOptionPane;

import com.agenda.UI.PlayerUI.PlayerDeletionDialog;
import com.agenda.UI.PlayerUI.PlayerRegistrationDialog;
import com.agenda.UI.PlayerUI.PlayerSearchDialog;

public class PlayerMenu {
    public void showMenuPlayer() {
        boolean exit = true;
        String option="""
            1. Add Player
            2. Remove  Player
            3. Search Player
            0. Exit
        """;
        while (exit) {
            try {
                String input = JOptionPane.showInputDialog(
                    null,
                    option,
                    "Menu Players",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (input == null) {
                    return;
                }
                int entrada = Integer.parseInt(input.trim());
                switch (entrada) {
                    case 1:
                        new PlayerRegistrationDialog(null).setVisible(true);
                        break;
                    case 2:
                        new PlayerDeletionDialog(null).setVisible(true);
                        break;
                    case 3:
                        new PlayerSearchDialog(null).setVisible(true);
                        break;
                    case 0:
                        exit = false;
                        break;
                
                    default:
                        JOptionPane.showMessageDialog(
                            null,
                            "La opcion que ingreso no es valida, por favor ingrese una opcion del menu.",
                            "Entrada inválida",
                            JOptionPane.ERROR_MESSAGE
                        );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Ingrese una opción numérica válida.",
                    "Entrada inválida",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
