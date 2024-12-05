package org.example.model;


import jakarta.persistence.*;
import org.example.Utils.Role;

/**
 * The {@code Admin} class represents an administrator, inheriting from the {@code Person} class.
 * It contains additional details specific to administrators, such as their assigned {@code Role}.
 */
@Entity
@Table(name = "admins")
public class Admin extends Person {
    /**
     * The role of the administrator.
     */


    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Constructs a new {@code Admin} with the specified ID, age, name, and role.
     *
     * @param age  the age of the administrator
     * @param name the name of the administrator
     * @param role the role assigned to the administrator
     */
    public Admin(int age, String name, Role role) {
        super(age, name);
        this.role = role;
    }

    public Admin(int age, String name) {
        super(age, name);
    }

    public Admin() {
        super();
    }

    /**
     * Returns the role assigned to this administrator.
     *
     * @return the role of the administrator
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets a new role for this administrator.
     *
     * @param role the new role to assign to the administrator
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the administrator, including the role information.
     *
     * @return a string representation of the administrator
     */
    @Override
    public String toString() {
        return super.toString() + ", Role: " + role;
    }
}
