package com.sep3yg9.njorddata.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import com.sep3yg9.njorddata.models.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
  User findById(int id);
}
