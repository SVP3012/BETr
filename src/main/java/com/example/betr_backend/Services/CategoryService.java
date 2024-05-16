package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.CategoryModels.CreateCategoryModel;
import com.example.betr_backend.Models.CategoryModels.MonthYearModel;
import com.example.betr_backend.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.betr_backend.Entities.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo cr;
    @Autowired
    private UserService us;
    @Autowired
    private ExpenseService es;
    @Autowired
    private BudgetService bs;
    @Autowired
    private BudgetCategoryService bcs;

    public Category createCategory(CreateCategoryModel category){
        Category newCategory = new Category();
        User user = us.fetchUser(category.getId());
        newCategory.setUser(user);
        newCategory.setName(category.getName());
        return cr.save(newCategory);
    }
    public void defaultCategories(long uid){
        String[] defaultcategories= {"Miscellaneous", "Food", "Travel", "Entertainment", "Savings"};
        for(int i=0; i<defaultcategories.length; i++)
        {
            CreateCategoryModel cat = new CreateCategoryModel();
            cat.setId(uid);
            cat.setName(defaultcategories[i]);
            createCategory(cat);
        }
    }
    public Category saveCategory(Category cat) { return cr.save(cat);}
    public Category findMiscCategory(long uid) {
        Category category = cr.findMisc(uid);
        return category;
    }
    public Category fetchCategory(long categoryId) { return cr.findByCategoryId(categoryId);}
    public String getNameOfCategory(long cid) { return cr.findNameOfId(cid);}
    public List<Category> getCategoriesOfUser(long uid) { return cr.findAllCategoriesOfUser(uid);}
    public Category updateCategoryName(CreateCategoryModel newCategory){
        Category category = fetchCategory(newCategory.getId());
        category.setName(newCategory.getName());
        return cr.save(category);
    }
    @Transactional
    public void deleteCategory(long cid, MonthYearModel mym){
        Category cat = fetchCategory(cid);
        long bid = bs.getBudgetId(cat.getUser().getUserId(), mym.getMonth(), mym.getYear());
        es.makeCategoryMiscWhenDeleted(cid);
//        es.deleteCategoryExpenses(cid);
        bcs.deleteUserCategories(bid, cid);
        cr.deleteCategory(cid);
    }
    public void deleteAllCategories(long uid){
        cr.deleteAllCategories(uid);
    }
}