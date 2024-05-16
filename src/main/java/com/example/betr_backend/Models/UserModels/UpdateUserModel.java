package com.example.betr_backend.Models.UserModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserModel {
    private long uid;
    private String name_or_pwd;
}