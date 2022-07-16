package za.co.wethinkcode.hackathon.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PasswordValidator implements Predicate {

    @Override
    public boolean test(Object password) {
        String pass = password.toString();
        if (pass.length() >= 8){
            return true;
        }
        return false;
    }
}
