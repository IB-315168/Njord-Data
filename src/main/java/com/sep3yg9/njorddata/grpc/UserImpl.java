package com.sep3yg9.njorddata.grpc;

import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import com.sep3yg9.njorddata.services.UserService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class UserImpl extends UserServiceGrpc.UserServiceImplBase
{
  @Autowired
  private UserService userService;

  @Override public void createUser(CreatingUser user, StreamObserver<User> responseObserver)
  {
    userService.addUser(new com.sep3yg9.njorddata.models.User(user.getFullName(),
        user.getEmail(), user.getUserName(), user.getPassword()));

    com.sep3yg9.njorddata.models.User userCreated = userService.getByUserName(
        user.getUserName());

    User user1 = User.newBuilder()
        .setId(userCreated.getIdmember())
        .setFullName(userCreated.getFullName())
        .setEmail(userCreated.getEmail())
        .setUserName(userCreated.getUserName())
        .setPassword(userCreated.getPassword())
        .build();

    System.out.println(userService.getById(userCreated.getIdmember()));

    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }
}
