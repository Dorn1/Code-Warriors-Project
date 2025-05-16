package pl.summarizer.codewarriorsproject.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Exception.AlreadyExistsException;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;

@RestController
@CrossOrigin("*")
public class AppUserController {
    AppUserService userService;

    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            if(userService.login(username, password)) {
                return ResponseEntity.ok(userService.getUserId(username).toString());
            }
        } catch (DoesntExistException e) {
            return ResponseEntity.status(401).body("Bad Credentials");
        }
        return ResponseEntity.status(401).body("Bad Credentials");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestParam String user, @RequestParam String password) {
        try{
            userService.addUser(user, password);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
        return ResponseEntity.ok("User" + user + "registered successfully");
    }
}
