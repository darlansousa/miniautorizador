package br.com.darlansilva.miniautorizador.core.domain;

import java.util.List;

import br.com.darlansilva.miniautorizador.core.common.UserRole;

public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final List<UserRole> roles;

    public User(Long id, String username, String password, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public static User from(Long id, String username, String password, List<UserRole> roles) {
        return new User(id, username, password, roles);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }
}
