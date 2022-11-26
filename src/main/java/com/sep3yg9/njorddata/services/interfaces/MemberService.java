package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.MemberEntity;

import java.util.List;

public interface MemberService
{
    void addUser(MemberEntity memberEntityRecord);
    void updateUser(UpdatingUser user);
    void deleteUser(int id);
    List<User> getAllUsers();
    MemberEntity getById(int id);
    MemberEntity getByUserName(String username);
    MemberEntity getByEmail(String email);
    Iterable<MemberEntity> getAll();

}
