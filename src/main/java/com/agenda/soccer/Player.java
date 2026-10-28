package com.agenda.soccer;

public class Player extends Federation {
    private String position;
    private String country;
    private int number;

    public Player(String name, String lastName, int age, String position, String country, int number) {
        super(name, lastName, age);
        this.position = position;
        this.country = country;
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // Getters and setters
}
