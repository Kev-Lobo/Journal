package in.kevinlobo.journalApp.controller;

import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.UserRepository;
import in.kevinlobo.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String HealthCheck() {
        return "OK";
    }
    @PostMapping("/create-user")
    public void saveEntry(@RequestBody User user) {
        userService.saveEntry(user);
    }

}
