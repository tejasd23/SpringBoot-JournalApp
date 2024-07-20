package net.tejasdagale.controller;

import net.tejasdagale.entity.User;
import net.tejasdagale.repository.UserRepository;
import net.tejasdagale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserContoller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public List<User> getallUser(){
//        return userService.findAll();
//    }

//    @PostMapping
//    public void createUser(@RequestBody User user){
//        userService.saveEntry(user);
//    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
        public ResponseEntity<?> deleteUsersById(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userRepository.deleteByUserName(authentication.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }



}
