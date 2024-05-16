package com.example.betr_backend.Repositories;

import com.example.betr_backend.Entities.Budget;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Integer> {

    Budget findByBudgetId(long budgetId);
    @Query("SELECT b.budgetId FROM Budget b where b.user.userId =:uid AND b.month=:m AND b.year=:y")
    int getBudgetIdForMonthYear(@Param("uid") long uid, @Param("m") int m, @Param("y") int y);
    @Query("SELECT b FROM Budget b WHERE b.user.userId = :uid")
    List<Budget> findAllBudgetsOfUser(@Param("uid") long uid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Budget b where b.budgetId=:bid")
    void deleteBudget(@Param("bid") long bid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Budget b where b.user.userId=:uid")
    void deleteAllBudgets(@Param("uid") long uid);


}