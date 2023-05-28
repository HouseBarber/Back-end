package com.projetoIntegradorII.HouseBarber.controller.user;

import com.projetoIntegradorII.HouseBarber.entity.user.User;
import com.projetoIntegradorII.HouseBarber.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> findAll(){
        return userService.findALl();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findByID(@PathVariable("id") Long id) {
        User user = userService.findByID(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public User insert(@RequestBody User user){
        return userService.insert(user);
    }

    @PutMapping("/")
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
