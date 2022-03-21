package rating.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rating.app.entity.OnlineBusiness;
import rating.app.entity.User;
import rating.app.model.GlobalResponse;
import rating.app.model.SignIn;
import rating.app.service.UserService;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<GlobalResponse> getUserDetails(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userService.getUserDetails(userId));
    }

    @PostMapping()
    public ResponseEntity<GlobalResponse> createUser(@Valid @RequestBody User user){
        GlobalResponse userResponse = userService.createUserDetails(user);
        return ResponseEntity.ok(userResponse);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<GlobalResponse> editUser(@RequestBody User user, @PathVariable("userId") String userId){
        user.setEmailId(userId);
        GlobalResponse response = userService.updateUserDetails(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<GlobalResponse> deleteUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("/{userId}/signin")
    public ResponseEntity<GlobalResponse> signin(@PathVariable String userId, @Valid @RequestBody SignIn signIn){
        return ResponseEntity.ok(userService.authenticateUser(signIn.getPassword(), userId));
    }

}
