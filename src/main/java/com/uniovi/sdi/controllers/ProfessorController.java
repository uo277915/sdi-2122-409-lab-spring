package com.uniovi.sdi.controllers;

import com.uniovi.sdi.entities.Professor;
import com.uniovi.sdi.services.ProfessorsService;
import com.uniovi.sdi.validators.AddProfessorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfessorController {

    @Autowired //Inyectar el servicio
    private ProfessorsService professorsService;

    @Autowired
    private AddProfessorValidator addProfessorValidator;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@Validated Professor professor, BindingResult result, Model model) {

        addProfessorValidator.validate(professor, result);

        if (result.hasErrors()) {
            return "/professor/add";
        }
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorsService.deleteProfessor(id);
        return "redirect:/professor/list";
    }

    @RequestMapping(value = "/professor/add")
    public String getProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/add";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/details";
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/edit";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id) {
        professor.setId(id);
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/" + id;
    }
}
