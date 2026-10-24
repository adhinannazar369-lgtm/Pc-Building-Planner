package com.aipc.planner.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String phone;
    @Column(nullable = false, length = 100)
    private String passwordHash;

    public AppUser() {}
    public AppUser(String phone, String passwordHash) {
        this.phone = phone; this.passwordHash = passwordHash;
    }
    public Long getId() { return id; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
