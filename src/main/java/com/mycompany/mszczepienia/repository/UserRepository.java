package com.mycompany.mszczepienia.repository;

import com.mycompany.mszczepienia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
