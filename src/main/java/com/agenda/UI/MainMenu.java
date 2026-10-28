package com.agenda.UI;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MainMenu {
    public void showMenu() {
        boolean exit = true;
        String option = """
                    1. Create Team
                    2. Create Player
                    3. Create Staff
                    0. Exit
                """;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Manejo de excepción alternativa
        }
        while (exit) {
            try {
                int entrada = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        option,
                        "Menu Principal",
                        JOptionPane.QUESTION_MESSAGE).trim());
                switch (entrada) {
                    case 1:
                        TeamMenu teamMenu = new TeamMenu();
                        teamMenu.showMenuTeam();
                        break;
                    case 2:
                        PlayerMenu playerMenu = new PlayerMenu();
                        playerMenu.showMenuPlayer();
                        break;
                    case 3:
                        StaffMenu staffMenu = new StaffMenu();
                        staffMenu.showMenuStaff();
                        break;
                    case 0:
                        exit = false;
                        break;

                    default:
                        JOptionPane.showMessageDialog(
                                null,
                                "La opcion que ingreso no es valida, por favor ingrese una opcion del menu.",
                                "Entrada inválida",
                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ingrese una opción numérica válida.",
                        "Entrada inválida",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
