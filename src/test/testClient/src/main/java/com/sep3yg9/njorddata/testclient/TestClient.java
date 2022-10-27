package com.sep3yg9.njorddata.testclient;

//import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
//import com.sep3yg9.njorddata.grpc.protobuf.user.User;
//import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TestClient
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
        .usePlaintext()
        .build();

    UserServiceGrpc.UserServiceBlockingStub stub
        = UserServiceGrpc.newBlockingStub(channel);

    User user = stub.createUser(CreatingUser.newBuilder()
        .setFullName("Igor Test")
        .setEmail("test@gmail.com")
        .setUserName("test_igor")
        .setPassword("1234")
        .build());

    System.out.println(user);
    channel.shutdown();
  }
}
