package com.sep3yg9.njorddata.testclient;

import com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserList;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class SearchClient
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
        .usePlaintext()
        .build();

    UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
    Scanner scanner = new Scanner(System.in);
    System.out.println("Type in full name:");
    String fullName = scanner.nextLine();
    System.out.println("Type in email:");
    String email = scanner.nextLine();
    System.out.println("Type in user name:");
    String userName = scanner.nextLine();
    UserList list = stub.searchUser(SearchingUser.newBuilder()
        .setFullName(fullName)
        .setEmail(email)
        .setUserName(userName)
        .build());

    for(User user : list.getUserList()) {
      System.out.println(user);
    }
  }
}
