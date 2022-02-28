package com.uniovi.sdi.validators;

import com.uniovi.sdi.entities.Professor;
import com.uniovi.sdi.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddProfessorValidator implements Validator {
    @Autowired
    private ProfessorsService professorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");

        if (professor.getDni().length() != 9 || !Character.isLetter(professor.getDni().charAt(8))) {
            errors.rejectValue("dni", "Error.professor.dni.format");
        } else {
            boolean incorrectDigit = false;
            for (int i = 0; i < professor.getDni().length() - 1; i++) {
                if (!Character.isDigit(professor.getDni().charAt(i))) incorrectDigit = true;
            }
            if (incorrectDigit)
                errors.rejectValue("dni", "Error.professor.dni.format");

        }

        if (professorService.getProfessorByDni(professor.getDni()) != null) {
            errors.rejectValue("dni", "Error.professor.dni.duplicate");
        }

        if (professor.getName().length() < 5 || professor.getName().length() > 24) {
            errors.rejectValue("name", "Error.professor.name.length");
        }

        if (professor.getSurname().length() < 5 || professor.getSurname().length() > 24) {
            errors.rejectValue("surname", "Error.professor.surname.length");
        }
    }
}
