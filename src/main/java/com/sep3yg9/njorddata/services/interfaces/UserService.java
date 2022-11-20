package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.UserEntity;

import java.util.List;

public interface UserService
{
    void addUser(UserEntity userEntityRecord);
    void updateUser(UpdatingUser user);
    void deleteUser(int id);
    List<User> getAllUsers();
    UserEntity getById(int id);
    UserEntity getByUserName(String username);
    UserEntity getByEmail(String email);
    Iterable<UserEntity> getAll();

}
