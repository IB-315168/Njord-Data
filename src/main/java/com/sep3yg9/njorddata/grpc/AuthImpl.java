package com.sep3yg9.njorddata.grpc;

import com.sep3yg9.njorddata.grpc.protobuf.auth.AuthServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.auth.LoginRequest;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.services.MemberServiceImpl;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService public class AuthImpl extends AuthServiceGrpc.AuthServiceImplBase
{
  private final MemberServiceImpl userService;

  public AuthImpl(MemberServiceImpl userService)
  {
    this.userService = userService;
  }

  @Override public void login(LoginRequest request,
      StreamObserver<User> responseObserver)
  {
    MemberEntity record = userService.getByEmail(request.getEmail());

    try
    {
      if (record != null && record.getPassword().equals(request.getPassword()))
      {
        User user = User.newBuilder().setId(record.getIdmember())
            .setFullName(record.getFullName()).setEmail(record.getEmail())
            .setUserName(record.getUserName()).setPassword(record.getPassword())
            .build();

        responseObserver.onNext(user);
        responseObserver.onCompleted();
      }
      else
      {
        throw new IllegalArgumentException("Email or password mismatch");
      }
    }
    catch (Exception e)
    {
      Status status;
      if (e instanceof IllegalArgumentException)
      {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      }
      else
      {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
  }
}
