package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.Budget;
import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.BudgetModels.CreateBudgetModel;
import com.example.betr_backend.Repositories.BudgetRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BudgetServiceTest {

    @Mock
    private BudgetRepo budgetRepo;

    @Mock
    private UserService userService;

    @Mock
    private BudgetCategoryService budgetCategoryService;

    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testCreateBudget() {
        // Mocking createBudgetModel
        CreateBudgetModel createBudgetModel = new CreateBudgetModel();
        createBudgetModel.setId(1L);
        createBudgetModel.setMonth(5);
        createBudgetModel.setYear(2024);
        createBudgetModel.setCatamtlist(new ArrayList<>());

        // Creating and setting the user for the budget
        User user = new User();
        user.setUserId(1L);
        when(userService.fetchUser(1L)).thenReturn(user);

        // Mocking new budget
        Budget newBudget = new Budget();
        newBudget.setBudgetId(1L);
        newBudget.setUser(user);
        newBudget.setMonth(createBudgetModel.getMonth());
        newBudget.setYear(createBudgetModel.getYear());

        // Mocking the repository behavior
        when(budgetRepo.save(any(Budget.class))).thenReturn(newBudget);

        // Performing the test
        Budget createdBudget = budgetService.createBudget(createBudgetModel);

        // Assertions
        assertEquals(createBudgetModel.getMonth(), createdBudget.getMonth());
        assertEquals(createBudgetModel.getYear(), createdBudget.getYear());
        assertEquals(user.getUserId(), createdBudget.getUser().getUserId());

        // Verify interactions
        verify(budgetRepo, times(2)).save(any(Budget.class));  // Save is called twice: once initially, once after category creation
        verify(budgetCategoryService, times(1)).createBudgetCategory(eq(newBudget.getUser().getUserId()), eq(newBudget.getBudgetId()), eq(createBudgetModel.getCatamtlist()));
    }

    @Test
    void testFetchBudget() {
        // Mocking expected budget
        Budget expectedBudget = new Budget();
        when(budgetRepo.findByBudgetId(1L)).thenReturn(expectedBudget);

        // Performing the test
        Budget fetchedBudget = budgetService.fetchBudget(1L);

        // Assertions
        assertEquals(expectedBudget, fetchedBudget);
    }

    @Test
    void testGetAllBudgets() {
        // Mocking expected budgets
        List<Budget> expectedBudgets = new ArrayList<>();
        when(budgetRepo.findAllBudgetsOfUser(1L)).thenReturn(expectedBudgets);

        // Performing the test
        List<Budget> fetchedBudgets = budgetService.getAllBudgets(1L);

        // Assertions
        assertEquals(expectedBudgets, fetchedBudgets);
    }
//    @Test
//    void testUpdateBudget() {
//        // Mocking newBudgetModel
//        CreateBudgetModel newBudgetModel = new CreateBudgetModel();
//        newBudgetModel.setId(1L);
//        newBudgetModel.setMonth(5);
//        newBudgetModel.setYear(2024);
//        newBudgetModel.setCatamtlist(new ArrayList<>());
//
//        // Mocking existing budget
//        Budget budget = new Budget();
//        budget.setBudgetId(1L);
//
//        // Creating and setting the user for the budget
//        User user = new User();
//        user.setUserId(1L);
//        budget.setUser(user);
//
//        // Mocking the repository behavior
//        when(budgetRepo.findByBudgetId(1L)).thenReturn(budget);
//        when(budgetRepo.save(any(Budget.class))).thenReturn(budget);
//
//        // Performing the test
//        Budget updatedBudget = budgetService.updateBudget(newBudgetModel);
//
//        // Assertions
//        assertEquals(newBudgetModel.getMonth(), updatedBudget.getMonth());
//        assertEquals(newBudgetModel.getYear(), updatedBudget.getYear());
//
//        // Verify interactions
//        verify(budgetRepo, times(1)).findByBudgetId(1L); // Ensure this method is called only once
//        verify(budgetRepo, times(1)).save(any(Budget.class));
//        verify(budgetCategoryService, times(1)).updateBudgetCategory(eq(budget.getUser().getUserId()), eq(budget.getBudgetId()), eq(newBudgetModel.getCatamtlist()));
//    }


    @Test
    void testGetBudgetId() {
        // Mocking expected budgetId
        long expectedBudgetId = 123L;
        when(budgetRepo.getBudgetIdForMonthYear(1L, 5, 2024)).thenReturn((int) expectedBudgetId);

        // Performing the test
        long fetchedBudgetId = budgetService.getBudgetId(1L, 5, 2024);

        // Assertions
        assertEquals(expectedBudgetId, fetchedBudgetId);
    }

    @Test
    void testDeleteBudget() {
        // Performing the test
        budgetService.deleteBudget(1L);

        // Verify interactions
        verify(budgetCategoryService, times(1)).deleteUserBudgets(1L);
        verify(budgetRepo, times(1)).deleteBudget(1L);
    }

    @Test
    void testDeleteAllBudgets() {
        // Performing the test
        budgetService.deleteAllBudgets(1L);

        // Verify interactions
        verify(budgetRepo, times(1)).deleteAllBudgets(1L);
    }
}