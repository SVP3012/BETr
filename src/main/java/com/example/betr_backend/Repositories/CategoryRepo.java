package com.example.betr_backend.Repositories;

import com.example.betr_backend.Entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByCategoryId(long categoryId);
    @Query("select c FROM Category c WHERE c.user.userId=:uid AND c.name='miscellaneous'")
    Category findMisc(@Param("uid") long uid);
    @Query("SELECT c.name FROM Category c WHERE c.categoryId=:cid")
    String findNameOfId(@Param("cid") long cid);
    @Query("SELECT c FROM Category c WHERE c.user.userId = :uid")
    List<Category> findAllCategoriesOfUser(@Param("uid") long uid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Category c where c.categoryId=:cid")
    void deleteCategory(@Param("cid") long cid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Category c where c.user.userId=:uid")
    void deleteAllCategories(@Param("uid") long uid);



}