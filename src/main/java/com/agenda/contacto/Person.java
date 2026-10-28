package com.agenda.contacto;

import java.util.Iterator;
import java.util.List;

public class Person {
    String name;
    String lastName;
    String phoneNumber;
    String email;
    String address;

    public Person() {
    }
    

    public Person(String name, String lastName, String phoneNumber, String email, String address) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void addPerson(List<Person> contacts) {
            System.out.println("Enter name:");
            String name = System.console().readLine();
            System.out.println("Enter last name:");
            String lastName = System.console().readLine();
            System.out.println("Enter phone number:");
            String phoneNumber = System.console().readLine();
            System.out.println("Enter email:");
            String email = System.console().readLine();
            System.out.println("Enter address:");
            String address = System.console().readLine();
            Person person = new Person(name, lastName, phoneNumber, email, address);
            contacts.add(person);
    }
    public Person searchPerson(List<Person> contacts){
        Iterator<Person> iterator = contacts.iterator();
        System.out.println("Enter name to search:");
        String name = System.console().readLine();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getName().equalsIgnoreCase(name)) {
                return person;
            }
        }
        return null;

    }
    public void searchPersonByLastName(List<Person> contacts){
        System.out.println("Enter Last name to search:");
        String name = System.console().readLine();
        contacts.forEach(myPerson ->{
            if (myPerson.getLastName().equalsIgnoreCase(name)) 
                {
                    System.out.println(myPerson.getName() + " " + myPerson.getLastName() + " - " + myPerson.getPhoneNumber() + " - " + myPerson.getEmail() + " - " + myPerson.getAddress());
                }
            });

    }
    public void removePerson(List<Person> contacts){
        System.out.println("Enter name to remove:");
        String name = System.console().readLine();
        Iterator<Person> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Person removed");
                return;
            }
        }
        System.out.println("Person not found");
    }
    public void removePerson(List<Person> contacts,String name){

        contacts.forEach(myPerson ->{
            if (myPerson.getLastName().equalsIgnoreCase(name)) 
                {
                    contacts.remove(contacts.indexOf(myPerson));
                    System.out.println("Person removed");
                }
            });
    }
}
