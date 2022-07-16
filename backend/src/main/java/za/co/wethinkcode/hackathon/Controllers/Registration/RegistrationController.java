package za.co.wethinkcode.hackathon.Controllers.Registration;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import za.co.wethinkcode.hackathon.LoginDto;
import za.co.wethinkcode.hackathon.Models.User;
import za.co.wethinkcode.hackathon.Request.RegistrationRequest;
import za.co.wethinkcode.hackathon.Service.AppUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class RegistrationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    private AppUserService service;


    @PostMapping(path = "/registration",produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody RegistrationRequest request, HttpServletResponse response){
        try {
            User user = new User(
                    request.getUserName(),
                    request.getPassword(),
                    request.getEmail(),
                    request.getUserRole());
            createCookie(response,user);

            return "";
        }catch (IllegalStateException e){
            return "";
        }

    }


    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Logging in");
            return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);

        }catch (DisabledException e){
            return new ResponseEntity<>("User has not verified email", HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return service.confirmToken(token);
    }


    public void createCookie(HttpServletResponse response, User user){
        Cookie cookie = new Cookie("username", user.getUsername());
        response.addCookie(cookie);

        int cookieAgeInSeconds = 3600;
        cookie.setMaxAge(cookieAgeInSeconds); // expire in 30 seconds
        response.addCookie(cookie);
//        deleteCookie(response,cookie);
    }

    public void deleteCookie(HttpServletResponse response, Cookie cookie){
        cookie.setMaxAge(0); // delete cookie
        response.addCookie(cookie);
    }
}
