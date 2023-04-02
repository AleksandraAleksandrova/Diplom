package org.elsys.diplom.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reset_password_tokens")
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="reset_password_token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    public ResetPasswordToken(User user) {
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
        LocalDateTime expirationTime = createdAt.plusHours(1);
        return now.isAfter(expirationTime);
    }
}
