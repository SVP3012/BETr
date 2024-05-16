package com.example.betr_backend.Services;

import com.example.betr_backend.Models.UserModels.UpdateUserModel;
import com.example.betr_backend.Repositories.UserRepo;
import com.example.betr_backend.Models.UserModels.CreateUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.betr_backend.Entities.User;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;
    @Autowired
    private CategoryService cs;
    @Autowired
    private BudgetService bs;
    @Autowired
    private ExpenseService es;
    @Autowired
    private BudgetCategoryService bcs;

    public User login(String username, String password) {
        return ur.findByUsernameAndPassword(username, password);
    }
    public User createUser(CreateUserModel user){
        User newUser = new User();
        newUser.setUsername(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(user.getPassword());
        newUser = ur.save(newUser);
        cs.defaultCategories(newUser.getUserId());
        return newUser;
    }
    public User fetchUser(long userId) { return ur.findUserByUserId(userId);}
    public User updateUserName(UpdateUserModel user){
        User updatedUser = fetchUser(user.getUid());
        updatedUser.setUsername(user.getName_or_pwd());
        return ur.save(updatedUser);
    }
    public User updateUserPwd(UpdateUserModel user){
        User updatedUser = fetchUser(user.getUid());
        updatedUser.setPasswordHash(user.getName_or_pwd());
        return ur.save(updatedUser);
    }
    public void deleteUser(long uid){
        bcs.deleteUserBudgetsAndCategories(uid);
        es.deleteAllExpenses(uid);
        bs.deleteAllBudgets(uid);
        cs.deleteAllCategories(uid);
        ur.deleteUser(uid);
    }
}