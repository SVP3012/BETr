package com.example.betr_backend.Controllers;

import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Entities.Expense;
import com.example.betr_backend.Models.BudgetModels.IdMonthYearModel;
import com.example.betr_backend.Models.ExpenseModels.CreateExpenseModel;
import com.example.betr_backend.Models.ExpenseModels.UpdateExpenseModel;
import com.example.betr_backend.Services.BudgetCategoryService;
import com.example.betr_backend.Services.BudgetService;
import com.example.betr_backend.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
@CrossOrigin
public class ExpenseCtrller {

    @Autowired
    private ExpenseService es;
    @Autowired
    private BudgetService bs;
    @Autowired
    private BudgetCategoryService bcs;

    @GetMapping("/getExpense/{eid}")
    public ResponseEntity<Expense> getExpense(@PathVariable long eid){
        Optional<Expense> expenseOptional = Optional.ofNullable(es.fetchExpense(eid));
        return expenseOptional.map(expense -> ResponseEntity.ok().body(expense))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/getAllExpensesOfCategory/{cid}")
    public ResponseEntity<List<Expense>> getAllExpensesOfCategory(@PathVariable long cid){
        List<Expense> expenses = es.getAllExpensesOfCategory(cid);
        return ResponseEntity.ok(expenses);
    }
    @GetMapping("/getAllExpensesOfUser/{uid}")
    public ResponseEntity<List<Expense>> getAllExpensesOfUser(@PathVariable long uid){
        List<Expense> expenses = es.getAllExpensesOfUser(uid);
        return ResponseEntity.ok(expenses);
    }
    @GetMapping("/getAllExpensesOfMonthYear")
    public ResponseEntity<List<Expense>> getAllExpensesOfMonthYear(@RequestBody IdMonthYearModel monthyear){
        long bid = bs.getBudgetId(monthyear.getUid(), monthyear.getMonth(), monthyear.getYear());
        List<Expense> allExpenses = new ArrayList<>();
        List<Category> categories = bcs.getCategoriesOfBudget(bid);
        for (Category category : categories) {
            List<Expense> expenses = es.getAllExpensesOfCategory(category.getCategoryId());
            allExpenses.addAll(expenses);
        }
        return ResponseEntity.ok(allExpenses);
    }
    @PostMapping("/createExpense")
    public ResponseEntity<Expense> createExpense(@RequestBody CreateExpenseModel expense){
        try{

            Expense newExpense = es.createExpense(expense);
            return ResponseEntity.of(Optional.of(newExpense));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateExpense")
    public ResponseEntity<Expense> updateExpense(@RequestBody UpdateExpenseModel expense){
        try{
            Expense updatedExpense = es.updateExpense(expense);
            return ResponseEntity.of(Optional.of(updatedExpense));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @DeleteMapping("/deleteExpense/{eid}")
    public ResponseEntity<String> deleteExpense(@PathVariable long eid, @RequestBody IdMonthYearModel monthYearExpense) {
        try {
            long bid = bs.getBudgetId(monthYearExpense.getUid(), monthYearExpense.getMonth(), monthYearExpense.getYear());
            es.deleteExpense(eid, bid);
            return ResponseEntity.ok("Record with ID " + eid + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting record with ID " + eid + ": " + e.getMessage());
        }
    }
}