package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.BudgetCategory;
import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Repositories.BudgetCategoryRepo;
import com.example.betr_backend.Models.BudgetCategoryModels.CatAmtList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Entities.Budget;
import java.util.List;

@Service
public class BudgetCategoryService {

    @Autowired
    private BudgetCategoryRepo bcr;
    @Autowired
    private UserService us;
    @Autowired
    private BudgetService bs;
    @Autowired
    private CategoryService cs;

    public void createBudgetCategory(long uid, long bid, List<CatAmtList> catamtlist) {

        User user = us.fetchUser(uid);
        Budget budget = bs.fetchBudget(bid);
        for (CatAmtList catAmt : catamtlist) {
            Category cat = cs.fetchCategory(catAmt.getCid());
            if(cat!=null){
                BudgetCategory newBC = new BudgetCategory();
                newBC.setUser(user);
                newBC.setBudget(budget);
                newBC.setCategory(cat);
                newBC.setAllocated_amount(catAmt.getAmt());
                newBC.setSpent_amount(0);
                newBC = bcr.save(newBC);
            }
        }
    }
    public void updateBudgetCategory(long uid, long bid, List<CatAmtList> catamtlist) {
        User user = us.fetchUser(uid);
        Budget budget = bs.fetchBudget(bid);
        for (CatAmtList catAmt : catamtlist) {
            BudgetCategory existingBC = bcr.findByUserAndBudgetAndCategory(uid, bid, catAmt.getCid());
            if (existingBC == null) {
                Category category = cs.fetchCategory(catAmt.getCid());
                existingBC = new BudgetCategory();
                existingBC.setUser(user);
                existingBC.setBudget(budget);
                existingBC.setCategory(category);
            }
            existingBC.setAllocated_amount(catAmt.getAmt());
            existingBC = bcr.save(existingBC);
        }
    }
    public List<BudgetCategory> getCategoriesOfBudget_BC(long bid) { return bcr.findBudgetCategoriesByBudgetId(bid);}
    public List<Category> getCategoriesOfBudget(long bid) { return bcr.findCategoriesByBudgetId(bid);}
    public void updateSpentAmount(long uid, long bid, long cid, long amount) { bcr.updateSpentAmount(uid, bid, cid, amount); }
    public void changeSpentAmount(long uid, long bid, long cid, long old, long amount) { bcr.changeSpentAmount(uid, bid, cid, old, amount); }
    public void reduceSpentAmount(long uid, long bid, long cid, long amount) { bcr.reduceSpentAmount(uid, bid, cid, amount); }
    public void deleteUserCategories(long bid, long cid) { bcr.deleteUserCategories(bid, cid);}
    public void deleteUserBudgetsAndCategories(long uid){
        bcr.deleteUserBudgetsAndCategories(uid);
    }
    public void deleteUserBudgets(long bid) { bcr.deleteUserBudgets(bid);}
}