package za.co.wethinkcode.hackathon.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class UserValidator implements Predicate {
    @Override
    public boolean test(Object o) {
        return true;
    }
}
