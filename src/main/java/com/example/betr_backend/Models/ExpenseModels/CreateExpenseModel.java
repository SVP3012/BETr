package com.example.betr_backend.Models.ExpenseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateExpenseModel {
    private long uid;
    private long cid;
    private int month;
    private int year;
    private long amount;
    private String desc;
    private Date date;
}