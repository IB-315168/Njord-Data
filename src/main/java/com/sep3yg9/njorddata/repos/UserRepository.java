package com.sep3yg9.njorddata.repos;

import org.springframework.data.repository.CrudRepository;

import com.sep3yg9.njorddata.models.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>
{
  UserEntity findById(int id);
  UserEntity findByUsername(String UserName);
  UserEntity findByEmail(String Email);
}
