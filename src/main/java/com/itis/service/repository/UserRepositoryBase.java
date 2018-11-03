package com.itis.service.repository;

import com.itis.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryBase<T extends User> extends JpaRepository<T, Long> {

    T findByEmail(String email);

}
