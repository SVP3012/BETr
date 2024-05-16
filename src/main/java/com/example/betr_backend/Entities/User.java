package com.example.betr_backend.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="User_ID")
    private Long userId;

    @Column(name="Username")
    private String username;

    @Column(name="Password")
    private String passwordHash;

    @Column(name="Email")
    private String email;

    @Column(name="Date_Of_Creation")
    private Date createdAt;

    @Column(name="Date_Of_Updation")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Expense> Expenses;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Budget> budgets;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BudgetCategory> budgetcategories;

}