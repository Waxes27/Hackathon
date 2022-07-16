package za.co.wethinkcode.hackathon.Service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.wethinkcode.hackathon.Controllers.Registration.token.ConfirmationToken;
import za.co.wethinkcode.hackathon.Controllers.Registration.token.ConfirmationTokenService;
import za.co.wethinkcode.hackathon.Models.User;
import za.co.wethinkcode.hackathon.Models.enums.AppUserRole;
import za.co.wethinkcode.hackathon.Repositories.UserRepository;
import za.co.wethinkcode.hackathon.Request.RegistrationRequest;
import za.co.wethinkcode.hackathon.validation.EmailValidator;
import za.co.wethinkcode.hackathon.validation.PasswordValidator;
import za.co.wethinkcode.hackathon.validation.UserValidator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final UserValidator userValidator;
//    private final EmailSender emailSender;
    private final EmailValidator emailValidator;
//    private final CompanyService companyService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;


    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token);

        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirm");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token Expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        this.enableUser(confirmationToken.getUser().getEmail());
        return "";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public String registerUser(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (userRepository.findByUsername(request.getUserName().toString()).isPresent()){
            throw new IllegalStateException("Username Already present");
        }

        if (!isValidEmail) {
            throw new IllegalStateException("Invalid Email Address");
        }

        if (!isValidPassword){
            throw new IllegalStateException("Password must be greater than 8 characters");
        }

        User user = new User(
                request.getUserName(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getUserRole()

        );



        user.setUserRole(AppUserRole.valueOf(request.getUserRole().toString()));

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken= new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );



        userRepository.save(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
//        emailSender.send(user.getEmail(),"waxes27.com/confirm?token="+token);
        System.out.println("Mail sent to"+user.getEmail());
        return token;
    }

    public void enableUser(String email){
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            throw new IllegalStateException("User not found!");
        }else {
            user.get().setEnabled(true);
        }
    }


    public String getCurrentUser(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        System.out.println(userOptional);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return user.toString();
        }
        else {
            throw new IllegalStateException("User '" +email+"' not found");
        }
    };


}
