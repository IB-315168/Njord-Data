package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import com.sep3yg9.njorddata.grpc.protobuf.team.*;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.services.TeamServiceImpl;
import com.sep3yg9.njorddata.services.UserServiceImpl;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@GRpcService
public class TeamImpl extends TeamServiceGrpc.TeamServiceImplBase
{
    @Autowired
    private TeamServiceImpl teamService;
    @Autowired
    private UserServiceImpl userService;

    @Override public void createTeam(CreatingTeam team, StreamObserver<Team> responseObserver)
    {
        TeamEntity nameCheck = teamService.getByName(team.getName());

        if(nameCheck != null) {
            System.out.println("Name is in use");

            User teamLeader = userService.getById(nameCheck.getTeamLeader()).convertToUser();

            responseObserver.onNext(Team.newBuilder()
                .setId(nameCheck.getIdTeam())
                .setTeamLeader(teamLeader)
                .build());
            responseObserver.onCompleted();
        }


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

        List<User> users = new ArrayList<>();
        for(TeamMember member : members) {
            users.add(member.getUserEntity().convertToUser());
        }

        User teamLeader = userService.getById(team.getTeamLeader()).convertToUser();

        Team team1 = Team.newBuilder()
                .setId(team.getIdTeam())
                .setName(team.getName())
                .setTeamLeader(teamLeader)
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

    @Override
    public void getByName(StringValue name, StreamObserver<Team> responseObserver) {
//        TeamEntity team = teamService.getByName(name.getValue());
    }

//    @Override
//    public void getByUserId(Int32Value id, StreamObserver<TeamList> responseObserver) {
//        List<BasicTeam> teams = new ArrayList<>();
//
//        UserEntity user = userService.getById(id.getValue());
//
//        for(TeamMember team : user.getTeams()) {
//            teams.add(team.getTeamEntity().convertToBasicTeam());
//        }
//    }
//    @Override
//    public void getByUserId(Int32Value id, StreamObserver<TeamList> responseObserver) {
//        Optional<TeamMember> teams = teamService.getByUserId(id.getValue());
//
//        List<Team> teamsUser = new ArrayList<>();
//        if(teams.isPresent()) {
//            for(TeamMember teamMember : teams.stream().toList()){
//                teamsUser.add(teamMember.getTeamEntity().convertToTeam());
//            }
//        }
//
//        TeamList list = TeamList.newBuilder().addAllTeam(teamsUser).build();
//        responseObserver.onNext(list);
//        responseObserver.onCompleted();
//    }
}
