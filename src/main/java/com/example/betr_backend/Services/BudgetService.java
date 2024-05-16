package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.BudgetModels.CreateBudgetModel;
import com.example.betr_backend.Repositories.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.betr_backend.Entities.Budget;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepo br;
    @Autowired
    private UserService us;
    @Autowired
    private BudgetCategoryService bcs;

    public Budget createBudget(CreateBudgetModel budget){
        Budget newBudget = new Budget();
        User user = us.fetchUser(budget.getId());
        newBudget.setUser(user);
        newBudget.setMonth(budget.getMonth());
        newBudget.setYear(budget.getYear());
        newBudget = br.save(newBudget);
        bcs.createBudgetCategory(newBudget.getUser().getUserId(), newBudget.getBudgetId(), budget.getCatamtlist());
        return br.save(newBudget);
    }
    public Budget fetchBudget(long budgetId) { return br.findByBudgetId(budgetId);}
    public List<Budget> getAllBudgets(long uid) { return br.findAllBudgetsOfUser(uid);}
    public Budget updateBudget(CreateBudgetModel newBudget){
        Budget budget = fetchBudget(newBudget.getId());
        budget.setMonth(newBudget.getMonth());
        budget.setYear(newBudget.getYear());
        budget = br.save(budget);
        bcs.updateBudgetCategory(budget.getUser().getUserId(), budget.getBudgetId(), newBudget.getCatamtlist());
        return br.save(budget);

    }
    public long getBudgetId(long uid, int month, int year){ return br.getBudgetIdForMonthYear(uid, month, year); }
    public void deleteBudget(long bid) {
        bcs.deleteUserBudgets(bid);
        br.deleteBudget(bid);
    }
    public void deleteAllBudgets(long uid) {br.deleteAllBudgets(uid);}
}