package org.example.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * The {@code Person} class represents a generic person with an ID, age, and name.
 * This class implements the {@code HasID} interface, which provides a method to retrieve the unique ID of the person.
 */

@MappedSuperclass
abstract class Person implements HasID {

    /**
     * The unique identifier for this person.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer ID;

    /**
     * The age of the person.
     */
    protected int age;

    /**
     * The name of the person.
     */
    protected String name;

    /**
     * Constructs a new {@code Person} with the specified ID, age, and name.
     *
     * @param age  the age of this person
     * @param name the name of this person
     */
    public Person(int age, String name) {

        this.age = age;
        this.name = name;
    }

    public Person() {
    }

    /**
     * Retrieves the unique identifier of this person.
     *
     * @return the ID of this person
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Sets a new ID for this person.
     *
     * @param ID the new ID to set for this person
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Retrieves the age of this person.
     *
     * @return the age of this person
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets a new age for this person.
     *
     * @param age the new age to set for this person
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retrieves the name of this person.
     *
     * @return the name of this person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for this person.
     *
     * @param name the new name to set for this person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the unique identifier of this person, fulfilling the {@code HasID} interface contract.
     *
     * @return the ID of this person
     */
    @Override
    public Integer getId() {
        return this.ID;
    }

    /**
     * Returns a string representation of this person, including their ID, age, and name.
     *
     * @return a string representation of this person
     */
    @Override
    public String toString() {
        return "ID: " + ID + ", Age: " + age + ", Name: " + name;
    }


    @Override
    public void setId(int andIncrement) {
        this.ID = andIncrement; // Assign the provided ID value
    }
}
