package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamList;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class TestGetUser
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
        .usePlaintext()
        .build();

    UserServiceGrpc.UserServiceBlockingStub stub
        = UserServiceGrpc.newBlockingStub(channel);

    Scanner scanner = new Scanner(System.in);
    System.out.println("Type in users id:");
    int id = scanner.nextInt();
    User user = stub.getById(Int32Value.newBuilder().setValue(id).build());
    System.out.println(user);
  }
}
