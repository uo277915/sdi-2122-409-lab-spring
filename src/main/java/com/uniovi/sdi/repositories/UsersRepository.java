package com.uniovi.sdi.repositories;


import com.uniovi.sdi.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);
}
