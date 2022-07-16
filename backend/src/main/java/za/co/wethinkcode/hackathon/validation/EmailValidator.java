package za.co.wethinkcode.hackathon.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class EmailValidator implements Predicate {
    @Override
    public boolean test(Object o) {
        Pattern pattern = Pattern.compile("@",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(o.toString());
        return matcher.find();
    }
}
