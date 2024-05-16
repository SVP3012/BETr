package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Entities.Expense;
import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.ExpenseModels.CreateExpenseModel;
import com.example.betr_backend.Models.ExpenseModels.UpdateExpenseModel;
import com.example.betr_backend.Repositories.ExpenseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepo expenseRepo;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BudgetService budgetService;

    @Mock
    private BudgetCategoryService budgetCategoryService;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testCreateExpense() {
        // Mocking CreateExpenseModel
        CreateExpenseModel createExpenseModel = new CreateExpenseModel();
        createExpenseModel.setUid(1L);
        createExpenseModel.setCid(2L);
        createExpenseModel.setAmount(100);
        createExpenseModel.setDesc("Test Expense");
        createExpenseModel.setDate(new Date());
        createExpenseModel.setMonth(5);
        createExpenseModel.setYear(2024);

        // Mocking userService.fetchUser
        User user = new User();
        user.setUserId(1L);
        when(userService.fetchUser(1L)).thenReturn(user);

        // Mocking categoryService.fetchCategory
        Category category = new Category();
        category.setCategoryId(2L);
        when(categoryService.fetchCategory(2L)).thenReturn(category);

        // Mocking budgetService.getBudgetId
        when(budgetService.getBudgetId(1L, 5, 2024)).thenReturn(3L);

        // Mocking expenseRepo.save
        when(expenseRepo.save(any(Expense.class))).thenAnswer(invocation -> {
            Expense expense = invocation.getArgument(0);
            expense.setExpenseId(1L); // Assuming expense ID is generated
            return expense;
        });

        // Performing the test
        Expense createdExpense = expenseService.createExpense(createExpenseModel);

        // Assertions
        assertNotNull(createdExpense);
        assertEquals(user, createdExpense.getUser());
        assertEquals(category, createdExpense.getCategory());
        assertEquals(100, createdExpense.getAmount());
        assertEquals("Test Expense", createdExpense.getDescription());
        assertEquals(createExpenseModel.getDate(), createdExpense.getDate());

        // Verify interactions
        verify(expenseRepo, times(1)).save(any(Expense.class));
        verify(budgetCategoryService, times(1)).updateSpentAmount(1L, 3L, 2L, 100);
    }


    @Test
    void testFetchExpense() {
        // Mocking expense id
        long expenseId = 1L;

        // Mocking expected expense
        Expense expectedExpense = new Expense();
        when(expenseRepo.findByExpenseId(expenseId)).thenReturn(expectedExpense);

        // Performing the test
        Expense fetchedExpense = expenseService.fetchExpense(expenseId);

        // Assertions
        assertEquals(expectedExpense, fetchedExpense);
    }

    @Test
    void testGetAllExpensesOfCategory() {
        // Mocking category id
        long categoryId = 1L;

        // Mocking expected expenses
        List<Expense> expectedExpenses = new ArrayList<>();
        when(expenseRepo.findAllExpensesInCategory(categoryId)).thenReturn(expectedExpenses);

        // Performing the test
        List<Expense> fetchedExpenses = expenseService.getAllExpensesOfCategory(categoryId);

        // Assertions
        assertEquals(expectedExpenses, fetchedExpenses);
    }
    @Test
    void testGetAllExpensesOfUser() {
        // Mocking user id
        long userId = 1L;

        // Mocking expected expenses
        List<Expense> expectedExpenses = new ArrayList<>();
        when(expenseRepo.findAllExpensesOfUser(userId)).thenReturn(expectedExpenses);

        // Performing the test
        List<Expense> fetchedExpenses = expenseService.getAllExpensesOfUser(userId);

        // Assertions
        assertEquals(expectedExpenses, fetchedExpenses);
    }
//    @Test
//    void testUpdateExpense() {
//        // Mocking updateExpenseModel
//        UpdateExpenseModel updateExpenseModel = new UpdateExpenseModel();
//        updateExpenseModel.setEid(1L);
//        updateExpenseModel.setCid(2L);
//        updateExpenseModel.setAmount(200);
//        updateExpenseModel.setDesc("Updated Description");
//        updateExpenseModel.setDate(new Date()); // Correct date type
//        updateExpenseModel.setMonth(5);
//        updateExpenseModel.setYear(2024);
//
//        // Mocking existing expense
//        Expense existingExpense = new Expense();
//        existingExpense.setExpenseId(1L);
//
//        // Mocking user for existing expense
//        User user = new User();
//        user.setUserId(1L);
//        existingExpense.setUser(user);
//
//        // Mocking category for existing expense
//        Category category = new Category();
//        category.setCategoryId(2L);
//        existingExpense.setCategory(category);
//
//        existingExpense.setAmount(100L);
//        when(expenseRepo.findByExpenseId(1L)).thenReturn(existingExpense);
//
//        // Mocking expenseRepo.save to return the updated expense
//        when(expenseRepo.save(any(Expense.class))).thenReturn(existingExpense);
//
//        // Mocking budgetService.getBudgetId
//        long budgetId = 3L;
//        when(budgetService.getBudgetId(user.getUserId(), 5, 2024)).thenReturn(budgetId);
//
//        // Performing the test
//        Expense updatedExpense = expenseService.updateExpense(updateExpenseModel);
//
//        // Assertions
//        assertNotNull(updatedExpense); // Ensure updatedExpense is not null
//        assertEquals(category, updatedExpense.getCategory());
//        assertEquals(200, updatedExpense.getAmount());
//        assertEquals("Updated Description", updatedExpense.getDescription());
//        assertEquals(updateExpenseModel.getDate(), updatedExpense.getDate());
//
//        // Verify interactions
//        verify(budgetCategoryService, times(1)).changeSpentAmount(user.getUserId(), budgetId, 2L, 100, 200);
//        verify(expenseRepo, times(1)).save(existingExpense);
//    }

    @Test
    void testDeleteExpense() {
        // Mocking expense id and budget id
        long expenseId = 1L;
        long budgetId = 2L;

        // Mocking existing expense
        Expense existingExpense = new Expense();
        User user = new User();
        user.setUserId(3L); // Setting a non-null user id
        existingExpense.setExpenseId(expenseId);
        existingExpense.setUser(user); // Setting the user
        existingExpense.setCategory(new Category());
        existingExpense.setAmount(50L);
        when(expenseRepo.findByExpenseId(expenseId)).thenReturn(existingExpense);

        // Performing the test
        expenseService.deleteExpense(expenseId, budgetId);

        // Verify interactions
        verify(expenseRepo, times(1)).deleteExpense(expenseId);
        verify(budgetCategoryService, times(1)).reduceSpentAmount(user.getUserId(), budgetId, existingExpense.getCategory().getCategoryId(), existingExpense.getAmount());
    }


    @Test
    void testUpdateCategory() {
        // Mocking expense id and category id
        long expenseId = 1L;
        long categoryId = 2L;

        // Mocking existing expense
        Expense existingExpense = new Expense();
        existingExpense.setExpenseId(expenseId);
        existingExpense.setUser(new User());
        existingExpense.setCategory(new Category());
        existingExpense.setAmount(50L);
        when(expenseRepo.findByExpenseId(expenseId)).thenReturn(existingExpense);

        // Mocking categoryService.fetchCategory
        Category category = new Category();
        category.setCategoryId(categoryId);
        when(categoryService.fetchCategory(categoryId)).thenReturn(category);

        // Mocking expenseRepo.save
        when(expenseRepo.save(existingExpense)).thenReturn(existingExpense);

        // Performing the test
        Expense updatedExpense = expenseService.updateCategory(expenseId, categoryId);

        // Assertions
        assertNotNull(updatedExpense);
        assertEquals(category, updatedExpense.getCategory());

        // Verify interactions
        verify(expenseRepo, times(1)).save(existingExpense);
    }

    @Test
    void testDeleteAllExpenses() {
        // Mocking user id
        long userId = 1L;

        // Performing the test
        expenseService.deleteAllExpenses(userId);

        // Verify interactions
        verify(expenseRepo, times(1)).deleteAllExpenses(userId);
    }

}