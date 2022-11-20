package com.sep3yg9.njorddata.grpc;

import com.sep3yg9.njorddata.grpc.protobuf.auth.AuthServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.auth.LoginRequest;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.services.UserServiceImpl;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class AuthImpl extends AuthServiceGrpc.AuthServiceImplBase
{
  @Autowired
  private UserServiceImpl userService;

  @Override
  public void login(LoginRequest request, StreamObserver<User> responseObserver) {
    UserEntity record = userService.getByEmail(request.getEmail());

    if(record != null && record.getPassword().equals(request.getPassword())) {
      User user = User.newBuilder()
          .setId(record.getIdmember())
          .setFullName(record.getFullName())
          .setEmail(record.getEmail())
          .setUserName(record.getUserName())
          .setPassword(record.getPassword())
          .build();

      responseObserver.onNext(user);
      responseObserver.onCompleted();
    } else {
      System.out.println("Email or password mismatch.");
      responseObserver.onCompleted();
    }
  }
}
