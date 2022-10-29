package com.sep3yg9.njorddata.testclient;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class TestRemovingTeamClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        TeamServiceGrpc.TeamServiceBlockingStub stub = TeamServiceGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type an id:");
        int id = Integer.parseInt(scanner.nextLine());

        var ent = stub.deleteTeam(Int32Value.of(id));
        System.out.println("Team removed with id: " + id);

        channel.shutdown();
    }
}
