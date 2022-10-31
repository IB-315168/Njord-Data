package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.CreatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
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

//    UserServiceGrpc.UserServiceBlockingStub stub
//        = UserServiceGrpc.newBlockingStub(channel);

    TeamServiceGrpc.TeamServiceBlockingStub stub = TeamServiceGrpc.newBlockingStub(channel);
//    User user = stub.createUser(CreatingUser.newBuilder()
//        .setFullName("Igor Test")
//        .setEmail("test_acc@gmail.com")
//        .setUserName("igor_test")
//        .setPassword("Test1!")
//        .build());
//    Scanner scanner = new Scanner(System.in);
//    System.out.println("Type in id:");
//    int id = Integer.parseInt(scanner.nextLine());
//    User ent = stub.getById(Int32Value.newBuilder().setValue(id).build());
//    System.out.println(ent);
//    Team team = stub.createTeam(CreatingTeam.newBuilder().setTeamLeaderId(14).setName("sep3yg9").build());
    Scanner scanner = new Scanner(System.in);
    System.out.println("Type an id:");
    int id = Integer.parseInt(scanner.nextLine());
    Team ent = stub.getById(Int32Value.newBuilder().setValue(id).build());
    System.out.println(ent);
    for (User user : ent.getMembersList())
    {
      System.out.println(user);
    }
    System.out.println(ent.getMembersList().size());


    channel.shutdown();
  }
}
