package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.StringValue;
import com.sep3yg9.njorddata.grpc.protobuf.team.CreatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class TestCreateTeam
{
  public static void main(String[] args)
  {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",6565)
        .usePlaintext()
        .build();
    TeamServiceGrpc.TeamServiceBlockingStub stub
        = TeamServiceGrpc.newBlockingStub(channel);

    Scanner scanner = new Scanner(System.in);
    System.out.println("Type teams name:");
    String name = scanner.nextLine();
//    Team team = stub.createTeam(CreatingTeam.newBuilder().setName(name).setTeamLeaderId(1).build());
    var team1 = stub.getByName(StringValue.newBuilder().setValue(name).build());
    System.out.println(team1);
  }
}
