package com.example.betr_backend.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.betr_backend.Entities.*;
import com.example.betr_backend.Models.BudgetCategoryModels.CatAmtList;
import com.example.betr_backend.Repositories.BudgetCategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BudgetCategoryServiceTest {

    @Mock
    private BudgetCategoryRepo bcr;

    @Mock
    private UserService us;

    @Mock
    private BudgetService bs;

    @Mock
    private CategoryService cs;

    @InjectMocks
    private BudgetCategoryService bcs;

    private User user;
    private Budget budget;
    private Category category;
    private CatAmtList catAmtList;
    private BudgetCategory budgetCategory;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        budget = new Budget();
        budget.setBudgetId(1L);

        category = new Category();
        category.setCategoryId(1L);

        catAmtList = new CatAmtList();
        catAmtList.setCid(1L);
        catAmtList.setAmt(1000L);

        budgetCategory = new BudgetCategory();
        budgetCategory.setUser(user);
        budgetCategory.setBudget(budget);
        budgetCategory.setCategory(category);
        budgetCategory.setAllocated_amount(1000L);
        budgetCategory.setSpent_amount(0);
    }

    @Test
    void testCreateBudgetCategory() {
        List<CatAmtList> catAmtLists = Arrays.asList(catAmtList);

        when(us.fetchUser(1L)).thenReturn(user);
        when(bs.fetchBudget(1L)).thenReturn(budget);
        when(cs.fetchCategory(1L)).thenReturn(category);
        when(bcr.save(any(BudgetCategory.class))).thenReturn(budgetCategory);

        bcs.createBudgetCategory(1L, 1L, catAmtLists);

        verify(us).fetchUser(1L);
        verify(bs).fetchBudget(1L);
        verify(cs).fetchCategory(1L);
        verify(bcr).save(any(BudgetCategory.class));
    }

    @Test
    void testUpdateBudgetCategory_ExistingCategory() {
        List<CatAmtList> catAmtLists = Arrays.asList(catAmtList);

        when(us.fetchUser(1L)).thenReturn(user);
        when(bs.fetchBudget(1L)).thenReturn(budget);
        when(bcr.findByUserAndBudgetAndCategory(1L, 1L, 1L)).thenReturn(budgetCategory);
        when(bcr.save(any(BudgetCategory.class))).thenReturn(budgetCategory);

        bcs.updateBudgetCategory(1L, 1L, catAmtLists);

        verify(us).fetchUser(1L);
        verify(bs).fetchBudget(1L);
        verify(bcr).findByUserAndBudgetAndCategory(1L, 1L, 1L);
        verify(bcr).save(any(BudgetCategory.class));
    }

    @Test
    void testUpdateBudgetCategory_NewCategory() {
        List<CatAmtList> catAmtLists = Arrays.asList(catAmtList);

        when(us.fetchUser(1L)).thenReturn(user);
        when(bs.fetchBudget(1L)).thenReturn(budget);
        when(bcr.findByUserAndBudgetAndCategory(1L, 1L, 1L)).thenReturn(null);
        when(cs.fetchCategory(1L)).thenReturn(category);
        when(bcr.save(any(BudgetCategory.class))).thenReturn(budgetCategory);

        bcs.updateBudgetCategory(1L, 1L, catAmtLists);

        verify(us).fetchUser(1L);
        verify(bs).fetchBudget(1L);
        verify(bcr).findByUserAndBudgetAndCategory(1L, 1L, 1L);
        verify(cs).fetchCategory(1L);
        verify(bcr).save(any(BudgetCategory.class));
    }

    @Test
    void testGetCategoriesOfBudget() {
        when(bcr.findCategoriesByBudgetId(1L)).thenReturn(Arrays.asList(category));

        List<Category> categories = bcs.getCategoriesOfBudget(1L);

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(category.getCategoryId(), categories.get(0).getCategoryId());

        verify(bcr).findCategoriesByBudgetId(1L);
    }

    @Test
    void testUpdateSpentAmount() {
        doNothing().when(bcr).updateSpentAmount(1L, 1L, 1L, 500L);

        bcs.updateSpentAmount(1L, 1L, 1L, 500L);

        verify(bcr).updateSpentAmount(1L, 1L, 1L, 500L);
    }

    @Test
    void testChangeSpentAmount() {
        doNothing().when(bcr).changeSpentAmount(1L, 1L, 1L, 500L, 1000L);

        bcs.changeSpentAmount(1L, 1L, 1L, 500L, 1000L);

        verify(bcr).changeSpentAmount(1L, 1L, 1L, 500L, 1000L);
    }

    @Test
    void testReduceSpentAmount() {
        doNothing().when(bcr).reduceSpentAmount(1L, 1L, 1L, 500L);

        bcs.reduceSpentAmount(1L, 1L, 1L, 500L);

        verify(bcr).reduceSpentAmount(1L, 1L, 1L, 500L);
    }

    @Test
    void testDeleteUserCategories() {
        doNothing().when(bcr).deleteUserCategories(1L, 1L);

        bcs.deleteUserCategories(1L, 1L);

        verify(bcr).deleteUserCategories(1L, 1L);
    }

    @Test
    void testDeleteUserBudgetsAndCategories() {
        doNothing().when(bcr).deleteUserBudgetsAndCategories(1L);

        bcs.deleteUserBudgetsAndCategories(1L);

        verify(bcr).deleteUserBudgetsAndCategories(1L);
    }

    @Test
    void testDeleteUserBudgets() {
        doNothing().when(bcr).deleteUserBudgets(1L);

        bcs.deleteUserBudgets(1L);

        verify(bcr).deleteUserBudgets(1L);
    }
}