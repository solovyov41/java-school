package com.tlg.core.entity;

import com.tlg.core.entity.enums.Role;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByEmail",
                query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findAllWithRole",
                query = "SELECT u FROM User u WHERE u.role = :role"),
        @NamedQuery(name = "User.findAllNotAssignedDriverUsers",
                query = "SELECT u FROM User u LEFT JOIN Driver dr ON dr.user=u WHERE u.role = :role AND dr.user is null")
})
public class User extends AbstractUpdatableEntity {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    private Role role;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail() != null && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("avatar='" + avatar + "'")
                .add("role=" + role)
                .toString();
    }
}
