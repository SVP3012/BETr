package com.example.betr_backend.Repositories;

import com.example.betr_backend.Entities.BudgetCategory;
import com.example.betr_backend.Entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BudgetCategoryRepo extends JpaRepository<BudgetCategory, Integer>{

    BudgetCategory findByBudgetcategoryId(long budgetcategoryId);
    @Query("SELECT bc FROM BudgetCategory bc WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
    BudgetCategory findByUserAndBudgetAndCategory(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid);
//    @Query("SELECT bc FROM BudgetCategory bc WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
//    List<BudgetCategory> findListByUserAndBudgetAndCategory(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid);

    @Query("SELECT bc FROM BudgetCategory bc WHERE bc.budget.budgetId=:bid")
    List<BudgetCategory> findBudgetCategoriesByBudgetId(@Param("bid") long bid);
    @Query("SELECT bc.category FROM BudgetCategory bc WHERE bc.budget.budgetId = :bid")
    List<Category> findCategoriesByBudgetId(@Param("bid") long bid);
    @Transactional
    @Modifying
    @Query("UPDATE BudgetCategory bc SET bc.allocated_amount =:amount WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
    void updateAllocatedAmount(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid, @Param("amount") long amount);
    @Transactional
    @Modifying
    @Query("UPDATE BudgetCategory bc SET bc.spent_amount = bc.spent_amount +:amount WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
    void updateSpentAmount(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid, @Param("amount") long amount);
    @Transactional
    @Modifying
    @Query("UPDATE BudgetCategory bc SET bc.spent_amount = bc.spent_amount -:old +:newAmt WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
    void changeSpentAmount(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid, @Param("old") long old, @Param("newAmt") long newAmt);
    @Transactional
    @Modifying
    @Query("UPDATE BudgetCategory bc SET bc.spent_amount = bc.spent_amount -:amount WHERE bc.user.userId=:uid AND bc.budget.budgetId=:bid AND bc.category.categoryId=:cid")
    void reduceSpentAmount(@Param("uid") long uid, @Param("bid") long bid, @Param("cid") long cid, @Param("amount") long amount);
    @Transactional
    @Modifying
    @Query("DELETE FROM BudgetCategory bc where bc.user.userId=:uid")
    void deleteUserBudgetsAndCategories(@Param("uid") long uid);
    @Transactional
    @Modifying
    @Query("DELETE FROM BudgetCategory bc where bc.budget.budgetId=:bid")
    void deleteUserBudgets(@Param("bid") long bid);
    @Transactional
    @Modifying
    @Query("DELETE FROM BudgetCategory bc where bc.category.categoryId=:cid AND bc.budget.budgetId=:bid")
    void deleteUserCategories(@Param("bid") long bid, @Param("cid") long cid);
}