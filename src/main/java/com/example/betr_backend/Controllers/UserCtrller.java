package com.example.betr_backend.Controllers;


import com.example.betr_backend.Entities.User;
import com.example.betr_backend.Models.UserModels.CreateUserModel;
import com.example.betr_backend.Models.UserModels.UpdateUserModel;
import com.example.betr_backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserCtrller {

    @Autowired
    private UserService us;

    @GetMapping("/getUser/{uid}")
    public ResponseEntity<User> getUser(@PathVariable long uid) {
        Optional<User> userOptional = Optional.ofNullable(us.fetchUser(uid));
        return userOptional.map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User user = us.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserModel user){
        try{
            User newUser = us.createUser(user);
            return ResponseEntity.of(Optional.of(newUser));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateUserName")
    public ResponseEntity<User> updateUserName(@RequestBody UpdateUserModel user){
        try{
            User updatedUser = us.updateUserName(user);

            return ResponseEntity.of(Optional.of(updatedUser));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateUserPwd")
    public ResponseEntity<User> updateUserPwd(@RequestBody UpdateUserModel user){
        try{
            User updatedUser = us.updateUserPwd(user);

            return ResponseEntity.of(Optional.of(updatedUser));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    @DeleteMapping("/deleteUser/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable long uid) {
        try {
            us.deleteUser(uid);
            return ResponseEntity.ok("Deleted user successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting user with ID " + uid + ": " + e.getMessage());
        }
    }
}