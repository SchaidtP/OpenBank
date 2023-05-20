package br.com.openbank.service.validate;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateService implements IValidateService{
    private static final Set<String> INVALID_CPFS = new HashSet<>(Arrays.asList(
            "00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
            "55555555555", "66666666666", "77777777777", "88888888888", "99999999999"
    ));

    public boolean validateCpf(String cpf) {
        cpf = removeSpecialCharacters(cpf);

        if (cpf.length() != 11 || INVALID_CPFS.contains(cpf)) {
            return false;
        }

        int sum = 0;
        int weight = 10;
        for (int i = 0; i < 9; i++) {
            int number = Character.getNumericValue(cpf.charAt(i));
            sum += (number * weight);
            weight--;
        }

        int result = 11 - (sum % 11);
        char tenthDigit = (result == 10 || result == 11) ? '0' : (char) (result + '0');

        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            int number = Character.getNumericValue(cpf.charAt(i));
            sum += (number * weight);
            weight--;
        }

        result = 11 - (sum % 11);
        char eleventhDigit = (result == 10 || result == 11) ? '0' : (char) (result + '0');

        return tenthDigit == cpf.charAt(9) && eleventhDigit == cpf.charAt(10);
    }

    private String removeSpecialCharacters(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    private static final String EMAIL_PATTERN = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
