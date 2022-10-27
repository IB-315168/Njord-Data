package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import com.sep3yg9.njorddata.models.UserEntity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.Console;
import java.util.Scanner;

public class TestClient
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
        .usePlaintext()
        .build();

    UserServiceGrpc.UserServiceBlockingStub stub
        = UserServiceGrpc.newBlockingStub(channel);

//    User user = stub.createUser(CreatingUser.newBuilder()
//        .setFullName("Igor Test")
//        .setEmail("test_acc@gmail.com")
//        .setUserName("igor_test")
//        .setPassword("Test1!")
//        .build());
    Scanner scanner = new Scanner(System.in);
    System.out.println("Type in id:");
    int id = Integer.parseInt(scanner.nextLine());
    User ent = stub.getById(Int32Value.newBuilder().setValue(id).build());
    System.out.println(ent);

    channel.shutdown();
  }
}
