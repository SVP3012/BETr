package com.example.betr_backend.Services;

import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.UserModels.CreateUserModel;
import com.example.betr_backend.Models.UserModels.UpdateUserModel;
import com.example.betr_backend.Repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BudgetService budgetService;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private BudgetCategoryService budgetCategoryService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        // Mocking CreateUserModel
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setName("Test User");
        createUserModel.setEmail("test@example.com");
        createUserModel.setPassword("password");

        // Mocking new User
        User newUser = new User();
        newUser.setUsername(createUserModel.getName());
        newUser.setEmail(createUserModel.getEmail());
        newUser.setPasswordHash(createUserModel.getPassword());
        newUser.setUserId(1L); // assuming the user gets an ID after being saved

        when(userRepo.save(any(User.class))).thenReturn(newUser);

        // Performing the test
        User createdUser = userService.createUser(createUserModel);

        // Assertions
        assertEquals("Test User", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("password", createdUser.getPasswordHash());

        // Verify interactions
        verify(categoryService, times(1)).defaultCategories(newUser.getUserId());
    }

    @Test
    void testFetchUser() {
        // Mocking user id and user
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setUsername("Test User");

        when(userRepo.findUserByUserId(userId)).thenReturn(user);

        // Performing the test
        User fetchedUser = userService.fetchUser(userId);

        // Assertions
        assertEquals(userId, fetchedUser.getUserId());
        assertEquals("Test User", fetchedUser.getUsername());
    }
    @Test
    void testUpdateUserName() {
        // Mocking UpdateUserModel
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setUid(1L);
        updateUserModel.setName_or_pwd("Updated User");

        // Mocking existing user
        User user = new User();
        user.setUserId(1L);
        user.setUsername("Test User");

        when(userRepo.findUserByUserId(1L)).thenReturn(user);

        // Mocking save method to return updated user
        when(userRepo.save(any(User.class))).thenReturn(user);

        // Performing the test
        User updatedUser = userService.updateUserName(updateUserModel);

        // Assertions
        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getUsername());

        // Verify interactions
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testUpdateUserPwd() {
        // Mocking UpdateUserModel
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setUid(1L);
        updateUserModel.setName_or_pwd("UpdatedPassword");

        // Mocking existing user
        User user = new User();
        user.setUserId(1L);
        user.setPasswordHash("OldPassword");

        when(userRepo.findUserByUserId(1L)).thenReturn(user);

        // Mocking save method to return updated user
        when(userRepo.save(any(User.class))).thenReturn(user);

        // Performing the test
        User updatedUser = userService.updateUserPwd(updateUserModel);

        // Assertions
        assertNotNull(updatedUser);
        assertEquals("UpdatedPassword", updatedUser.getPasswordHash());

        // Verify interactions
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        // Mocking user id
        long userId = 1L;

        // Performing the test
        userService.deleteUser(userId);

        // Verify interactions
        verify(budgetCategoryService, times(1)).deleteUserBudgetsAndCategories(userId);
        verify(expenseService, times(1)).deleteAllExpenses(userId);
        verify(budgetService, times(1)).deleteAllBudgets(userId);
        verify(categoryService, times(1)).deleteAllCategories(userId);
        verify(userRepo, times(1)).deleteUser(userId);
    }
}