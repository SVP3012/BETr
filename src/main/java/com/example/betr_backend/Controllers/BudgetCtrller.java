package com.example.betr_backend.Controllers;

import com.example.betr_backend.Entities.Budget;
import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Models.BudgetModels.CreateBudgetModel;
import com.example.betr_backend.Services.BudgetCategoryService;
import com.example.betr_backend.Services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("api/budget")
@CrossOrigin
public class BudgetCtrller {
    @Autowired
    private BudgetService bs;
    @Autowired
    private BudgetCategoryService bcs;

    @GetMapping("/getBudget/{bid}")
    public ResponseEntity<Map<String, Object>> getBudgetWithCategories(@PathVariable long bid) {
        Optional<Budget> budgetOptional = Optional.ofNullable(bs.fetchBudget(bid));
        if (budgetOptional.isPresent()) {
            Budget budget = budgetOptional.get();
            List<Category> categories = bcs.getCategoriesOfBudget(budget.getBudgetId());
            Map<String, Object> response = new HashMap<>();
            response.put("budget", budget);
            response.put("categories", categories);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllBudgets/{uid}")
    public ResponseEntity<List<Map<String, Object>>> getAllBudgets(@PathVariable long uid) {
        List<Budget> budgets = bs.getAllBudgets(uid);
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (Budget budget : budgets) {
            List<Category> categories = bcs.getCategoriesOfBudget(budget.getBudgetId());
            Map<String, Object> response = new HashMap<>();
            response.put("budget", budget);
            response.put("categories", categories);
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }
    @PostMapping("/createBudget")
    public ResponseEntity<Budget> createBudget(@RequestBody CreateBudgetModel budget){
        try{
            Budget newBudget = bs.createBudget(budget);
            return ResponseEntity.of(Optional.of(newBudget));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateBudget")
    public ResponseEntity<Budget> updateBudget(@RequestBody CreateBudgetModel budget){
        try{
            Budget updatedBudget = bs.updateBudget(budget);
            return ResponseEntity.of(Optional.of(updatedBudget));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @DeleteMapping("/deleteBudget/{bid}")
    public ResponseEntity<String> deleteBudget(@PathVariable long bid) {
        try {
            bs.deleteBudget(bid);
            return ResponseEntity.ok("Record with ID " + bid + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting record with ID " + bid + ": " + e.getMessage());
        }
    }
}