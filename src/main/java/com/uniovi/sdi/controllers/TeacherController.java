package com.uniovi.sdi.controllers;

import com.uniovi.sdi.entities.Teacher;
import com.uniovi.sdi.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TeacherController {

    @Autowired //Inyectar el servicio
    private TeachersService teachersService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("teacherList", teachersService.getTeachers());
        return "professor/list";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teachersService.addTeacher(teacher);
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "redirect:/professor/list";
    }

    @RequestMapping(value = "/professor/add")
    public String getTeacher() {
        return "professor/add";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "professor/details";
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "professor/edit";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Teacher teacher, @PathVariable Long id) {
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "redirect:/professor/details/" + id;
    }
}
