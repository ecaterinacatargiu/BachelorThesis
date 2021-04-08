package com.example.wally.validators;

import com.example.wally.domain.SimpleUser;
import com.example.wally.exceptions.ValidatorException;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class SimpleUserValidator implements Validator<SimpleUser>{


    @Override
    public void validate(SimpleUser entity) throws ValidatorException {

        boolean isNotNull = false;
        try
        {
            isNotNull = Stream.of(entity.getID(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getBalance(), entity.getTransactions()).noneMatch(Objects::isNull);
        }
        catch(NullPointerException ignored)
        {
            assert true;
        }


        boolean isFirstNameValid = entity.getFirstName().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
        boolean isLastNameValid = entity.getLastName().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(entity.getEmail());

        boolean isEmailValid = matcher.find();
        boolean isBalanceValid = DoubleStream.of(entity.getBalance()).filter(x -> x>= 0).findAny().isPresent();

        List<Boolean> finale = new ArrayList<>();
        finale.add(isNotNull);
        finale.add(isFirstNameValid);
        finale.add(isLastNameValid);
        finale.add(isEmailValid);
        finale.stream().filter(x -> !x).findAny().ifPresent(a -> { throw new ValidatorException("SimpleUser is not valid: " + "usr: " + Long.toString(entity.getID()) + ", " + entity.getFirstName().toString() + ", " + entity.getLastName() + ", "+entity.getEmail().toString() + ", " + entity.getPassword().toString() + ", " + entity.getTransactions() + "\n" ); });
    }
}
