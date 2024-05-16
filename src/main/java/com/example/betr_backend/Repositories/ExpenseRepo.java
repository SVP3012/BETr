package com.example.betr_backend.Repositories;

import com.example.betr_backend.Entities.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

    Expense findByExpenseId(long expenseId);
    @Query("SELECT e FROM Expense e WHERE e.category.categoryId = :cid")
    List<Expense> findAllExpensesInCategory(@Param("cid") long cid);
    @Query("SELECT e FROM Expense e WHERE e.user.userId = :uid")
    List<Expense> findAllExpensesOfUser(@Param("uid") long uid);
    @Query("SELECT e FROM Expense e WHERE e.user.userId = :uid ORDER BY e.date DESC LIMIT 5")
    List<Expense> findRecentExpensesOfUser(@Param("uid") long uid);
    @Transactional
    @Modifying
    @Query("UPDATE Expense e SET e.category.categoryId =:newcid WHERE e.category.categoryId=:oldcid")
    void updateCategoryToMisc(@Param("oldcid") long oldcid, @Param("newcid") long newcid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e where e.expenseId=:eid")
    void deleteExpense(@Param("eid") long eid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e where e.category.categoryId=:cid")
    void deleteCategoryExpenses(@Param("cid") long cid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e where e.user.userId=:uid")
    void deleteAllExpenses(@Param("uid") long uid);


}