package com.agenda.UI;

import javax.swing.JOptionPane;

public class TeamMenu {
 public void showMenuTeam() {
        boolean exit = true;
        String option="""
            1. Add Team
            2. Remove Team
            3. Search Team
            4. List of Teams
            0. Exit
        """;
        while (exit) {
            try {
                int entrada = Integer.parseInt(JOptionPane.showInputDialog(
                    null,
                    option,
                    "Menu Staff",
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
