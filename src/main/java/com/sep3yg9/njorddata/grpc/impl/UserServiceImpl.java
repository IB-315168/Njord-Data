package com.sep3yg9.njorddata.grpc.impl;

import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase
{
  @Override
  public void createUser(CreatingUser user, StreamObserver<User> responseObserver) {
    //pass to database
    User user1 = User.newBuilder()
        .setId(1)
        .setFullName(user.getFullName())
        .setEmail(user.getEmail())
        .setUserName(user.getUserName())
        .setEmail(user.getPassword())
        .build();

    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }
}
