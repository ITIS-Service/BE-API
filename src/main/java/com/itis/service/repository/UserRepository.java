package com.itis.service.repository;

import com.itis.service.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends UserRepositoryBase<User> {

}
