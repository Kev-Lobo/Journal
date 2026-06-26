package in.kevinlobo.journalApp.controller;

import in.kevinlobo.journalApp.apiResponse.WeatherResponse;
import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.UserRepository;
import in.kevinlobo.journalApp.service.UserService;
import in.kevinlobo.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greetings")
    public ResponseEntity<?> Greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = "";
        if(weatherResponse!=null) {
            greetings = ", Weather feels like :" + weatherResponse.main.feelsLike;
        }
       return new ResponseEntity<>("Hi " + authentication.getName()+greetings, HttpStatus.OK);
    }

}