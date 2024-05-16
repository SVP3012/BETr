package com.example.betr_backend.Services;

import com.example.betr_backend.Models.ExpenseModels.UpdateExpenseModel;
import com.example.betr_backend.Repositories.ExpenseRepo;
import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Models.ExpenseModels.CreateExpenseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.betr_backend.Entities.Expense;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepo er;
    @Autowired
    private UserService us;
    @Autowired
    private CategoryService cs;
    @Autowired
    private BudgetService bs;
    @Autowired
    private BudgetCategoryService bcs;

    public Expense createExpense(CreateExpenseModel expense){
        long bid = bs.getBudgetId(expense.getUid(), expense.getMonth(), expense.getYear());
        Expense newExpense = new Expense();
        User user = us.fetchUser(expense.getUid());
        Category category = cs.fetchCategory(expense.getCid());
        newExpense.setUser(user);
        newExpense.setCategory(category);
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDesc());
        newExpense.setDate(expense.getDate());
        bcs.updateSpentAmount(expense.getUid(), bid, expense.getCid(), expense.getAmount());
        return er.save(newExpense);
    }
    public Expense fetchExpense(long expenseId) { return er.findByExpenseId(expenseId);}
    public List<Expense> findRecentTrans(long uid) { return er.findRecentExpensesOfUser(uid);}
    public List<Expense> getAllExpensesOfCategory(long cid) { return er.findAllExpensesInCategory(cid);}
    public List<Expense> getAllExpensesOfUser(long uid) { return er.findAllExpensesOfUser(uid);}
    public Expense updateExpense(UpdateExpenseModel expense){
        Expense updatedExpense = fetchExpense(expense.getEid());
        long bid = bs.getBudgetId(updatedExpense.getUser().getUserId(), expense.getMonth(), expense.getYear());
        Category category = cs.fetchCategory(expense.getCid());
        updatedExpense.setCategory(category);
        long oldAmount = updatedExpense.getAmount();
        updatedExpense.setAmount(expense.getAmount());
        updatedExpense.setDescription(expense.getDesc());
        updatedExpense.setDate(expense.getDate());
        bcs.changeSpentAmount(updatedExpense.getUser().getUserId(), bid, expense.getCid(), oldAmount, expense.getAmount());
        return er.save(updatedExpense);
    }
    public void deleteExpense(long eid, long bid) {
        Expense expense = fetchExpense(eid);
        bcs.reduceSpentAmount(expense.getUser().getUserId(), bid, expense.getCategory().getCategoryId(), expense.getAmount());
        er.deleteExpense(eid);
    }
    public Expense updateCategory(long eid, long cid) {
        Expense updatedExpense = fetchExpense(eid);
        Category category = cs.fetchCategory(cid);
        updatedExpense.setCategory(category);
        return er.save(updatedExpense);
    }
    public void makeCategoryMiscWhenDeleted(long cid) {
        Category category = cs.fetchCategory(cid);
        category = cs.findMiscCategory(category.getUser().getUserId());
        cs.saveCategory(category);
        er.updateCategoryToMisc(cid, category.getCategoryId());
    }
    public void deleteAllExpenses(long uid) {er.deleteAllExpenses(uid);}
}