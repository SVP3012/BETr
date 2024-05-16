package com.example.betr_backend.Models.BudgetModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IdMonthYearModel {
    private int uid;
    private int month;
    private int year;
}