package com.agenda.UI;

import javax.swing.JOptionPane;

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
                int entrada = Integer.parseInt(JOptionPane.showInputDialog(
                    null,
                    option,
                    "Menu Players",
                    JOptionPane.QUESTION_MESSAGE
                ).trim());
                switch (entrada) {
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
