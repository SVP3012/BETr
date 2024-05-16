package com.example.betr_backend.Models.CategoryModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCategoryModel {
    private long id; //uid for create and cid for update
    private String name;
}