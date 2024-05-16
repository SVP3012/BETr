package com.example.betr_backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "budget_category")
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Budget_Category_ID")
    private Long budgetcategoryId;

    @Column(name="AllocatedAmount")
    private long allocated_amount;

    @Column(name="SpentAmount")
    private long spent_amount;

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

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Budget_ID")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;

}