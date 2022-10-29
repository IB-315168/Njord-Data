package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import com.sep3yg9.njorddata.services.UserService;
import com.sep3yg9.njorddata.models.UserEntity;
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
    userService.addUser(new UserEntity(user.getFullName(),
        user.getEmail(), user.getUserName(), user.getPassword()));

    UserEntity userCreated = userService.getByUserName(
        user.getUserName());

    User user1 = User.newBuilder()
        .setId(userCreated.getIdmember())
        .setFullName(userCreated.getFullName())
        .setEmail(userCreated.getEmail())
        .setUserName(userCreated.getUserName())
        .setPassword(userCreated.getPassword())
        .build();

    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }

  @Override
  public void updateUser(UpdatingUser user, StreamObserver<Empty> responseObserver) {
    userService.updateUser(user);
    responseObserver.onNext(Empty.newBuilder().build());
    responseObserver.onCompleted();
  }

  @Override
  public void getById(Int32Value id, StreamObserver<User> responseObserver) {
    UserEntity user = userService.getById(id.getValue());

    User user1 = User.newBuilder()
        .setId(user.getIdmember())
        .setFullName(user.getFullName())
        .setEmail(user.getEmail())
        .setUserName(user.getUserName())
        .setPassword(user.getPassword())
        .build();

    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }
}
