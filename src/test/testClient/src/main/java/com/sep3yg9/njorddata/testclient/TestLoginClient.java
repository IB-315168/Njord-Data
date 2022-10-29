package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.auth.AuthServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.auth.LoginRequest;
import com.sep3yg9.njorddata.grpc.protobuf.auth.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class TestLoginClient
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
        .usePlaintext()
        .build();
    AuthServiceGrpc.AuthServiceBlockingStub stub = AuthServiceGrpc.newBlockingStub(channel);

    Scanner scanner = new Scanner(System.in);
    System.out.println("Type in email:");
    String email = scanner.nextLine();
    System.out.println("Type in password:");
    String password = scanner.nextLine();
    User ent = stub.login(LoginRequest.newBuilder()
        .setEmail(email)
        .setPassword(password)
        .build());
    System.out.println("Hello, " + ent.getFullName());
  }
}
