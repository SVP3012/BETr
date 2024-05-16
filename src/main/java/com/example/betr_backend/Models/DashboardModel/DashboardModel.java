package com.example.betr_backend.Models.DashboardModel;

import com.example.betr_backend.Entities.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashboardModel {
    private double totalAmt;
    private double totalSpent;
    private List<Expense> expenses;
    private List<CategoryListModel> clms;
}