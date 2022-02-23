package com.uniovi.sdi.validators;

import com.uniovi.sdi.entities.Mark;
import com.uniovi.sdi.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddMarkValidator implements Validator {
    @Autowired
    private MarksService marksService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;

        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.mark.score.range");
        }

        if (mark.getDescription().length() < 20) {
            errors.rejectValue("description", "Error.mark.description.length");
        }
    }
}
