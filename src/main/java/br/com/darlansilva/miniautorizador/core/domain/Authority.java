package br.com.darlansilva.miniautorizador.core.domain;

public class Authority {
    private final Long id;
    private final User user;
    private final String role;

    private Authority(Long id, User user, String role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }
    public static Authority newAuthority(User user, String authority) {
        return new Authority(null, user, authority);
    }
    public static Authority from(Long id, User user, String authority) {
        return new Authority(id, user, authority);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }
}
