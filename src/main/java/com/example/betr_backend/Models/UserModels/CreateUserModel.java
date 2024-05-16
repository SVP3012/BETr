package com.example.betr_backend.Models.UserModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserModel {
    private String name;
    private String email;
    private String password;
}