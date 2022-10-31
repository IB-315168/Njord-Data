package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.EmptyOrBuilder;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.*;
import com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.TeamMemberId;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.services.TeamService;
import com.sep3yg9.njorddata.services.UserService;
import io.grpc.stub.StreamObserver;
import net.bytebuddy.description.type.TypeList;
import org.hibernate.Hibernate;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
//        ArrayList<User> members = new ArrayList<>();
//        for(UserEntity user : team.getMembers()) {
//            members.add(user.convertToUser());
//        }

        List<TeamMember> members = team.getMembers();
        System.out.println(members.size());

        List<User> users = new ArrayList<>();
        for(TeamMember member : members) {
            users.add(member.getUserEntity().convertToUser());
        }

        Team team1 = Team.newBuilder()
                .setId(team.getIdTeam())
                .setName(team.getName())
                .addAllMembers(users)
                .build();

        responseObserver.onNext(team1);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTeam(Int32Value id, StreamObserver<Empty> responseObserver) {
        teamService.removeTeam(id.getValue());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateTeam(UpdatingTeam team, StreamObserver<Empty> responseObserver){
        teamService.updateTeam(team);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
