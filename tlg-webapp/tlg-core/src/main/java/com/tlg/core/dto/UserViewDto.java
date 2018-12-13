package com.tlg.core.dto;

import com.tlg.core.entity.enums.Role;
import com.tlg.core.validation.annotations.EmailNotExists;
import com.tlg.core.validation.annotations.Matches;
import com.tlg.core.validation.groups.DatabaseChecks;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.RegistrationChecks;
import com.tlg.core.validation.groups.RoleChangeChecks;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Matches(first = "password", second = "confirmPassword", message = "{validation.passwords.dontMatch}", groups = RegistrationChecks.class)
public class UserViewDto {

    @Email(groups = EditChecks.class)
    @NotEmpty(groups = EditChecks.class)
    @EmailNotExists(groups = DatabaseChecks.class)
    @Size(max = 45, groups = EditChecks.class)
    private String email;

    @NotEmpty(groups = EditChecks.class)
    @Size(max = 45, groups = EditChecks.class)
    private String name;

    @NotEmpty(groups = EditChecks.class)
    @Size(max = 45, groups = EditChecks.class)
    private String surname;

    @NotEmpty(groups = RegistrationChecks.class)
    @Size(min = 5, max = 60, groups = RegistrationChecks.class)
    private String password;

    @NotEmpty(groups = RegistrationChecks.class)
    private String confirmPassword;

    @NotNull(groups = RoleChangeChecks.class)
    private Role role;

    private String avatar;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserViewDto userViewDto = (UserViewDto) o;
        return Objects.equals(email, userViewDto.email) &&
                Objects.equals(name, userViewDto.name) &&
                Objects.equals(surname, userViewDto.surname) &&
                Objects.equals(password, userViewDto.password) &&
                Objects.equals(confirmPassword, userViewDto.confirmPassword) &&
                Objects.equals(avatar, userViewDto.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, surname, password, confirmPassword, avatar);
    }
}
