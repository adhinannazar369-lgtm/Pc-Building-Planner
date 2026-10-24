package com.aipc.planner.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "saved_builds")
public class SavedBuild {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AppUser user;
    @Column(nullable = false, length = 40)
    private String purpose;
    @Column(nullable = false)
    private int budget;
    @Column(nullable = false, length = 4000)
    private String componentsJson;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SavedBuild() {}
    public SavedBuild(AppUser user, String purpose, int budget, String componentsJson) {
        this.user = user; this.purpose = purpose; this.budget = budget;
        this.componentsJson = componentsJson; this.createdAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public String getPurpose() { return purpose; }
    public int getBudget() { return budget; }
    public String getComponentsJson() { return componentsJson; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
