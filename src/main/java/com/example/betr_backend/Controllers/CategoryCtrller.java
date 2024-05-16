package com.example.betr_backend.Controllers;

import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Models.CategoryModels.CreateCategoryModel;
import com.example.betr_backend.Models.CategoryModels.MonthYearModel;
import com.example.betr_backend.Services.BudgetCategoryService;
import com.example.betr_backend.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/category")
@CrossOrigin
public class CategoryCtrller {

    @Autowired
    private CategoryService cs;
    @Autowired
    private BudgetCategoryService bcs;

    @GetMapping("/getCategory/{cid}")
    public ResponseEntity<Category> getCategory(@PathVariable long cid){
        Optional<Category> categoryOptional = Optional.ofNullable(cs.fetchCategory(cid));
        return categoryOptional.map(category -> ResponseEntity.ok().body(category))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/getCategoriesOfBudget/{bid}")
    public ResponseEntity<List<Category>> getCategoriesOfBudget(@PathVariable long bid){
        List<Category> budgetcats = bcs.getCategoriesOfBudget(bid);
        return ResponseEntity.ok(budgetcats);
    }
    @GetMapping("/getCategoriesOfUser/{uid}")
    public ResponseEntity<List<Category>> getCategoriesOfUser(@PathVariable long uid){
        List<Category> cats = cs.getCategoriesOfUser(uid);
        return ResponseEntity.ok(cats);
    }
    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryModel category){
        try{

            Category newCategory = cs.createCategory(category);
            return ResponseEntity.of(Optional.of(newCategory));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateCategoryName")
    public ResponseEntity<Category> updateCategoryName(@RequestBody CreateCategoryModel category){
        try{

            Category updatedCategory = cs.updateCategoryName(category);
            return ResponseEntity.of(Optional.of(updatedCategory));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @DeleteMapping("/deleteCategory/{cid}")
    public ResponseEntity<String> deleteCategory(@PathVariable long cid, @RequestBody MonthYearModel mym) {
        try {
            cs.deleteCategory(cid, mym);
            return ResponseEntity.ok("Record with ID " + cid + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting record with ID " + cid + ": " + e.getMessage());
        }
    }
}