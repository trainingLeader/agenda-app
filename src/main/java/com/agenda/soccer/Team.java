package com.agenda.soccer;

import java.util.Map;

public class Team {
    private String name;
    private String country;
    private int yearFounded;
    private Map<String, Player> players;
    private Map<String, Staff> staff;

    public Team(String name, String country, int yearFounded) {
        this.name = name;
        this.country = country;
        this.yearFounded = yearFounded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<String, Staff> getStaff() {
        return staff;
    }

    public void setStaff(Map<String, Staff> staff) {
        this.staff = staff;
    }
    
}
