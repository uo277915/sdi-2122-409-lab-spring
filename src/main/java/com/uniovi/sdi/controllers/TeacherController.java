package com.uniovi.sdi.controllers;

import com.uniovi.sdi.entities.Teacher;
import com.uniovi.sdi.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {

    @Autowired
    private TeachersService teachersService;

    @RequestMapping("/teacher/list")
    public String getList(Model model) {
        String str = "";

        for (Teacher teacher : teachersService.getTeachers()) {
            str += teacher.toString() + "\n\n";
        }

        return str;
    }

    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teachersService.addTeacher(teacher);
        return "Profesor con datos: " + teacher.toString() + " ha sido añadido!";
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "Profesor eliminado con exito!";
    }

    @RequestMapping("/teacher/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        return teachersService.getTeacher(id).toString();
    }

    @RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Teacher teacher, @PathVariable Long id) {
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "Profesor editado con exito! \n " + teachersService.getTeacher(id).toString();
    }
}
