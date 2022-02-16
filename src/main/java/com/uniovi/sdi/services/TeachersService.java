package com.uniovi.sdi.services;

import com.uniovi.sdi.entities.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachersService {

    // Lista porque de momento no usaremos la base de datos.
    List<Teacher> teachers = new ArrayList<>();

    public TeachersService() {
        teachers.add(new Teacher(0L, "58433957S", "Pepe", "Rico", "Matemáticas"));
        teachers.add(new Teacher(1L, "695168415A", "Diego", "Martín", "Física"));
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Teacher getTeacher(Long id) {
        return teachers.get(id.intValue());
    }

    public void addTeacher(Teacher teacher) {
        // Si en Id es null le asignamos el último + 1 de la lista
        if (teacher.getId() == null)
            teacher.setId(teachers.get(teachers.size() - 1).getId() + 1);
        teachers.add(teacher);
    }

    public void editTeacher(int id, Teacher teacher) {
        if (teachers.get(id) == null)
            return;

        if (teacher.getDni() != null) {
            teachers.get(id).setDni(teacher.getDni());
        }
        if (teacher.getName() != null) {
            teachers.get(id).setName(teacher.getName());
        }
        if (teacher.getSurname() != null) {
            teachers.get(id).setSurname(teacher.getSurname());
        }
        if (teacher.getCategory() != null) {
            teachers.get(id).setCategory(teacher.getCategory());
        }
    }

    public void deleteTeacher(int id) {
        teachers.remove(id);
    }
}