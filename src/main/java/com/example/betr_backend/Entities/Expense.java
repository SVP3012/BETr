package com.example.betr_backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Expense_ID")
    private Long expenseId;

    @Column(name="Amount")
    private Long amount;

    @Column(name="Description")
    private String description;

    @Column(name="Date")
    private Date date;

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
    @JoinColumn(name = "Category_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;


}