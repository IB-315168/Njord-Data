package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.CreatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.team.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.services.TeamService;
import com.sep3yg9.njorddata.services.UserService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class TeamImpl extends TeamServiceGrpc.TeamServiceImplBase
{
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @Override public void createTeam(CreatingTeam team, StreamObserver<Team> responseObserver)
    {
        teamService.addTeam(new TeamEntity(team.getTeamLeaderId(), team.getName()));

        TeamEntity teamCreated = teamService.getByName(team.getName());
        UserEntity userLeader = userService.getById(teamCreated.getTeamLeader());

        User teamLeader = User.newBuilder()
            .setId(userLeader.getIdmember())
            .setFullName(userLeader.getFullName())
            .setEmail(userLeader.getEmail())
            .setPassword(userLeader.getPassword())
            .build();

        Team team1 = Team.newBuilder()
                .setId(teamCreated.getIdTeam())
                .setTeamLeader(teamLeader)
                .setName(teamCreated.getName())
                .build();

        responseObserver.onNext(team1);
        responseObserver.onCompleted();
    }

    @Override public void getById(Int32Value id, StreamObserver<Team> responseObserver){
        TeamEntity team = teamService.getById(id.getValue());

        Team team1 = Team.newBuilder()
                .setId(team.getIdTeam())
                .setName(team.getName())
                .build();

        responseObserver.onNext(team1);
        responseObserver.onCompleted();
    }
}
