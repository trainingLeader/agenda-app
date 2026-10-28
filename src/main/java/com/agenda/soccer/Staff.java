package com.agenda.soccer;

public class Staff extends Federation {
    private String role;

    public Staff(String name, String lastName, int age, String role) {
        super(name, lastName, age);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
