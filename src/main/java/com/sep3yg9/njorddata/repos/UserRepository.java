package com.sep3yg9.njorddata.repos;

import org.springframework.data.repository.CrudRepository;

import com.sep3yg9.njorddata.models.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
  User findById(int id);
  User findByUsername(String UserName);
}
