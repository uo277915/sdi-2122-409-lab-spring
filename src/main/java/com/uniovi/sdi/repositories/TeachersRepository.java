package com.uniovi.sdi.repositories;

import com.uniovi.sdi.entities.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeachersRepository extends CrudRepository<Teacher, Long> {
}