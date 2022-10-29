package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class TestUpdateTeamClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",6565)
                .usePlaintext()
                .build();
        TeamServiceGrpc.TeamServiceBlockingStub stub
                = TeamServiceGrpc.newBlockingStub(channel);

        System.out.println("Team before update");
        var ent1 = stub.getById(Int32Value.newBuilder().setValue(15).build());
        System.out.println(ent1);
        System.out.println("Type new team's name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        var ent2 = stub.updateTeam(UpdatingTeam.newBuilder().setId(15).setName(name).build());
        System.out.println("Team after update");
        var ent3 = stub.getById(Int32Value.newBuilder().setValue(15).build());
        System.out.println(ent3);



    }
}
