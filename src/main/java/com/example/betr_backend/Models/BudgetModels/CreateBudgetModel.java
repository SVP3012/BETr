package com.example.betr_backend.Models.BudgetModels;

import com.example.betr_backend.Models.BudgetCategoryModels.CatAmtList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateBudgetModel {
    private long Id; //uid for create and bid for update
    private int month;
    private int year;
    List<CatAmtList> catamtlist;
}