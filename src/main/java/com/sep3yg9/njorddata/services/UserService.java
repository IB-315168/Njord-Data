package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser;
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

  public void updateUser(UpdatingUser user) {
    UserEntity userEntity = userRepository.findById(user.getId());

    if(userEntity == null) {
      System.out.println("User does not exist");
    } else
    {
      if (!user.getEmail().isEmpty() && !user.getEmail().equals(userEntity.getEmail()))
      {
        userEntity.setEmail(user.getEmail());
      }

      if (!user.getUserName().isEmpty() && !user.getUserName().equals(userEntity.getUserName()))
      {
        userEntity.setUserName(user.getUserName());
      }
      if (!user.getPassword().isEmpty() && !user.getPassword().equals(userEntity.getPassword()))
      {
        userEntity.setPassword(user.getPassword());
      }

      userRepository.save(userEntity);
    }
  }

  public UserEntity getById(int id) {
    return userRepository.findById(id);
  }

  public UserEntity getByUserName(String username) {
    return userRepository.findByUsername(username);
  }

  public UserEntity getByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
