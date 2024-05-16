package com.example.betr_backend.Models.DashboardModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryListModel {
    private String name;
    private double all_amt;
    private double spent_amt;
}