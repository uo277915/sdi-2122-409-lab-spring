package com.uniovi.sdi.repositories;

import com.uniovi.sdi.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorsRepository extends CrudRepository<Professor, Long> {
    Professor findByDni(String dni);
}