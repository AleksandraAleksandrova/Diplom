package org.elsys.diplom.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="confirmation_token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name="created_at")
    public LocalDateTime createdAt;

    public ConfirmationToken() {
    }

    public ConfirmationToken(User user) {
        this.user = user;
        token = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = createdAt.plusMinutes(30);
        return now.isAfter(expirationTime);
    }
}
