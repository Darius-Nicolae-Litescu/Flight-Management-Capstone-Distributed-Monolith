package org.darius.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageValidator implements ConstraintValidator<WordsNotAllowedConstraint, String> {
    List<String> badWords = new ArrayList<>();

    public MessageValidator(){
        badWords.addAll(Arrays.asList("Word1", "Word2"));
    }

    @Override
    public boolean isValid(String message, ConstraintValidatorContext constraintValidatorContext) {
        return badWords.stream().noneMatch(message::contains);
    }

}