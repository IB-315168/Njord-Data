package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
  @Autowired
  private UserRepository userRepository;
  public List<UserEntity> getAllUsers()
  {
    List<UserEntity> userEntityRecords = new ArrayList<>();
    userRepository.findAll().forEach(userEntityRecords::add);
    return userEntityRecords;
  }
  public void addUser(UserEntity userEntityRecord)
  {
    userRepository.save(userEntityRecord);
  }

  public UserEntity getById(int id) {
    return userRepository.findById(id);
  }

  public UserEntity getByUserName(String username) {
    return userRepository.findByUsername(username);
  }
}
