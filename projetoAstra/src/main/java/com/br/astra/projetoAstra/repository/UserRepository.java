package com.br.astra.projetoAstra.repository;

import com.br.astra.projetoAstra.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findById(long id);

    @Query(value="select * from login_register.user where email = :email and password = :password", nativeQuery = true)
    public User login(String email, String password);
}