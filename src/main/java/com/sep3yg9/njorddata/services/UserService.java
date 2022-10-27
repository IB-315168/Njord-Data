package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.models.User;
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
  public List<User> getAllUsers()
  {
    List<User>userRecords = new ArrayList<>();
    userRepository.findAll().forEach(userRecords::add);
    return userRecords;
  }
  public void addUser(User userRecord)
  {
    userRepository.save(userRecord);
  }

  public User getById(int id) {
    return userRepository.findById(id);
  }

  public User getByUserName(String username) {
    return userRepository.findByUsername(username);
  }
}
