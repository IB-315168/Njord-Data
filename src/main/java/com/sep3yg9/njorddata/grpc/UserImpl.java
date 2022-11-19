package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.user.*;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.TeamMemberId;
import com.sep3yg9.njorddata.services.TeamService;
import com.sep3yg9.njorddata.services.UserService;
import com.sep3yg9.njorddata.models.UserEntity;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@GRpcService
public class UserImpl extends UserServiceGrpc.UserServiceImplBase
{
  @Autowired
  private UserService userService;
  @Autowired
  private TeamService teamService;

  @Override public void createUser(CreatingUser user, StreamObserver<User> responseObserver)
  {
    userService.addUser(new UserEntity(user.getFullName(),
        user.getEmail(), user.getUserName(), user.getPassword()));

    UserEntity userCreated = userService.getByUserName(
        user.getUserName());

    User user1 = userCreated.convertToUser();

    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }

  @Override
  public void updateUser(UpdatingUser user, StreamObserver<Empty> responseObserver) {
    userService.updateUser(user);
    responseObserver.onNext(Empty.newBuilder().build());
    responseObserver.onCompleted();
  }

  @Override public void deleteUser(Int32Value id, StreamObserver<Empty> responseObserver) {
    userService.deleteUser(id.getValue());
  }

  @Override
  public void getById(Int32Value id, StreamObserver<User> responseObserver) {
    UserEntity user = userService.getById(id.getValue());

    ArrayList<BasicTeam> teamMembership = new ArrayList<>();
    for(TeamMember member : user.getTeams()) {
      teamMembership.add(member.getTeamEntity().convertToBasicTeam());
    }

    ArrayList<BasicTeam> teamsLeaders = new ArrayList<>();
    for(TeamEntity teamEntity : teamService.getByTeamLeaderId(user.getIdmember())) {
      teamsLeaders.add(teamEntity.convertToBasicTeam());
    }

    User user1 = User.newBuilder()
        .setId(user.getIdmember())
        .setFullName(user.getFullName())
        .setEmail(user.getEmail())
        .setUserName(user.getUserName())
        .setPassword(user.getPassword())
        .addAllUserTeams(teamMembership)
        .addAllUserTeams(teamsLeaders)
        .build();
//
//    User user1 = user.convertToUser();
    responseObserver.onNext(user1);
    responseObserver.onCompleted();
  }

  @Override
  public void getByEmail(com.google.protobuf.StringValue email, StreamObserver<User> responseObserver) {
    User user = userService.getByEmail(email.getValue()).convertToUser();

    responseObserver.onNext(user);
    responseObserver.onCompleted();
  }

  @Override
  public void searchUser(SearchingUser query, StreamObserver<UserList> responseObserver) {
    ArrayList<User> userList = new ArrayList<>(userService.getAllUsers());

    if(!query.getFullName().isEmpty())
    {
      userList.removeIf(user -> !user.getFullName().toLowerCase().contains(query.getFullName().toLowerCase()));
    }

    if(!query.getUserName().isEmpty()) {
      userList.removeIf(user -> !user.getUserName().toLowerCase().contains(query.getUserName().toLowerCase()));
    }

    if(!query.getEmail().isEmpty()) {
      userList.removeIf(user -> !user.getEmail().toLowerCase().contains(query.getEmail().toLowerCase()));
    }

    UserList searchResults = UserList.newBuilder().addAllUser(userList).build();

    responseObserver.onNext(searchResults);
    responseObserver.onCompleted();
  }
}
