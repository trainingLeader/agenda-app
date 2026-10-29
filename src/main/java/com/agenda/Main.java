package com.agenda;


import com.agenda.UI.MainMenu;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.showMenu();
        });
    }
}
