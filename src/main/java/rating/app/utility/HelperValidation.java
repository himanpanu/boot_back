package rating.app.utility;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperValidation {


    public static void validateEmail(String email){

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new RuntimeException("invalid email id format");
        }

    }

}
