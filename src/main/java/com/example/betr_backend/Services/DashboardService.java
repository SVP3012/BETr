package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.BudgetCategory;
import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Models.BudgetModels.IdMonthYearModel;
import com.example.betr_backend.Models.DashboardModel.CategoryListModel;
import com.example.betr_backend.Models.DashboardModel.DashboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private BudgetService bs;
    @Autowired
    private CategoryService cs;
    @Autowired
    private ExpenseService es;
    @Autowired
    private BudgetCategoryService bcs;

    public DashboardModel DashboardDetails(IdMonthYearModel imy) {
        long bid = bs.getBudgetId(imy.getUid(), imy.getMonth(), imy.getYear());
        List<BudgetCategory> budcats = bcs.getCategoriesOfBudget_BC(bid);
        DashboardModel dashboard = new DashboardModel();
        double BtotalAllocatedAmount = 0;
        double BtotalSpentAmount = 0;
        Map<Long, Double> allocatedAmountPerCategory = new HashMap<>();
        Map<Long, Double> spentAmountPerCategory = new HashMap<>();
        for (BudgetCategory budcat : budcats) {
            BtotalAllocatedAmount += budcat.getAllocated_amount();
            BtotalSpentAmount += budcat.getSpent_amount();
            long categoryId = budcat.getCategory().getCategoryId();
            allocatedAmountPerCategory.put(categoryId, allocatedAmountPerCategory.getOrDefault(categoryId, 0.0) + budcat.getAllocated_amount());
            spentAmountPerCategory.put(categoryId, spentAmountPerCategory.getOrDefault(categoryId, 0.0) + budcat.getSpent_amount());
        }
        dashboard.setTotalAmt(BtotalAllocatedAmount);
        dashboard.setTotalSpent(BtotalSpentAmount);
        List<CategoryListModel> categoryDetailsList = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : allocatedAmountPerCategory.entrySet()) {
            long categoryId = entry.getKey();
            String name = cs.getNameOfCategory(categoryId);
            double allocatedAmount = entry.getValue();
            double spentAmount = spentAmountPerCategory.getOrDefault(categoryId, 0.0);
            CategoryListModel categoryDetails = new CategoryListModel(name, allocatedAmount, spentAmount);
            categoryDetailsList.add(categoryDetails);
        }
        dashboard.setClms(categoryDetailsList);
        dashboard.setExpenses(es.findRecentTrans(imy.getUid()));
        return dashboard;
    }
}