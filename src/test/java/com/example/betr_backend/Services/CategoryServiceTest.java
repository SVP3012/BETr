package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.Category;
import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.CategoryModels.CreateCategoryModel;
import com.example.betr_backend.Models.CategoryModels.MonthYearModel;
import com.example.betr_backend.Repositories.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private UserService userService;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private BudgetService budgetService;

    @Mock
    private BudgetCategoryService budgetCategoryService;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
//    @Test
//    void testCreateCategory() {
//        // Mocking createCategoryModel
//        CreateCategoryModel createCategoryModel = new CreateCategoryModel();
//        createCategoryModel.setId(1L);
//        createCategoryModel.setName("Test Category");
//
//        // Create a mock User object
//        User user = new User();
//        user.setUserId(1L); // Set the user ID
//        // You can set other properties as needed
//
//        // Configure the mock behavior for userService.fetchUser(1L)
//        when(userService.fetchUser(1L)).thenReturn(user);
//
//        // Mocking categoryRepo.save
//        Category savedCategory = new Category();
//        when(categoryRepo.save(any(Category.class))).thenReturn(savedCategory);
//
//        // Performing the test
//        Category createdCategory = categoryService.createCategory(createCategoryModel);
//
//        // Assertions
//        assertEquals(user, createdCategory.getUser());
//        assertEquals("Test Category", createdCategory.getName());
//
//        // Verify interactions
//        verify(categoryRepo, times(1)).save(any(Category.class));
//    }
//    @Test
//    void testDefaultCategories() {
//        // Mocking categoryService
//        CategoryService categoryServiceMock = Mockito.mock(CategoryService.class);
//
//        // Mocking user id
//        long userId = 1L;
//
//        // Performing the test
//        categoryServiceMock.defaultCategories(userId);
//
//        // Verify interactions
//        verify(categoryServiceMock, times(5)).createCategory(any(CreateCategoryModel.class));
//    }



    @Test
    void testSaveCategory() {
        // Mocking category
        Category category = new Category();

        // Mocking categoryRepo.save
        when(categoryRepo.save(category)).thenReturn(category);

        // Performing the test
        Category savedCategory = categoryService.saveCategory(category);

        // Assertions
        assertEquals(category, savedCategory);

        // Verify interactions
        verify(categoryRepo, times(1)).save(category);
    }

    @Test
    void testFindMiscCategory() {
        // Mocking user id
        long userId = 1L;

        // Mocking expected misc category
        Category expectedCategory = new Category();
        when(categoryRepo.findMisc(userId)).thenReturn(expectedCategory);

        // Performing the test
        Category fetchedCategory = categoryService.findMiscCategory(userId);

        // Assertions
        assertEquals(expectedCategory, fetchedCategory);
    }

    @Test
    void testFetchCategory() {
        // Mocking category id
        long categoryId = 1L;

        // Mocking expected category
        Category expectedCategory = new Category();
        when(categoryRepo.findByCategoryId(categoryId)).thenReturn(expectedCategory);

        // Performing the test
        Category fetchedCategory = categoryService.fetchCategory(categoryId);

        // Assertions
        assertEquals(expectedCategory, fetchedCategory);
    }

    @Test
    void testGetCategoriesOfUser() {
        // Mocking user id
        long userId = 1L;

        // Mocking expected categories
        List<Category> expectedCategories = new ArrayList<>();
        when(categoryRepo.findAllCategoriesOfUser(userId)).thenReturn(expectedCategories);

        // Performing the test
        List<Category> fetchedCategories = categoryService.getCategoriesOfUser(userId);

        // Assertions
        assertEquals(expectedCategories, fetchedCategories);
    }

    @Test
    void testUpdateCategoryName() {
        // Mocking newCategoryModel
        CreateCategoryModel newCategoryModel = new CreateCategoryModel();
        newCategoryModel.setId(1L);
        newCategoryModel.setName("Updated Test Category");

        // Mocking existing category
        Category category = new Category();
        category.setCategoryId(1L);
        when(categoryRepo.findByCategoryId(1L)).thenReturn(category);

        // Mocking categoryRepo.save
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        // Performing the test
        Category updatedCategory = categoryService.updateCategoryName(newCategoryModel);

        // Assertions
        assertEquals(newCategoryModel.getName(), updatedCategory.getName());

        // Verify interactions
        verify(categoryRepo, times(1)).save(any(Category.class));
    }
    @Test
    void testDeleteCategory() {
        // Mocking category id
        long categoryId = 1L;

        // Mocking category
        Category category = new Category();
        // Create a mock User object
        User user = new User();
        user.setUserId(1L); // Set the user ID
        // Set the user in the category
        category.setUser(user);

        // Mocking MonthYearModel
        MonthYearModel monthYearModel = new MonthYearModel();
        monthYearModel.setMonth(5);
        monthYearModel.setYear(2024);

        // Mocking fetchCategory
        when(categoryService.fetchCategory(categoryId)).thenReturn(category);

        // Performing the test
        categoryService.deleteCategory(categoryId, monthYearModel);

        // Verify interactions
        verify(expenseService, times(1)).makeCategoryMiscWhenDeleted(categoryId);
        // You need to ensure that the user is not null before invoking getUserId()
        assertNotNull(category.getUser());
        verify(budgetService, times(1)).getBudgetId(category.getUser().getUserId(), 5, 2024);
        verify(budgetCategoryService, times(1)).deleteUserCategories(anyLong(), anyLong());
        verify(categoryRepo, times(1)).deleteCategory(categoryId);
    }

    @Test
    void testDeleteAllCategories() {
        // Mocking user id
        long userId = 1L;

        // Performing the test
        categoryService.deleteAllCategories(userId);

        // Verify interactions
        verify(categoryRepo, times(1)).deleteAllCategories(userId);
    }
}