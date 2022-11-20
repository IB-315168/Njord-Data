package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.user.*;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.services.TeamServiceImpl;
import com.sep3yg9.njorddata.services.UserServiceImpl;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;

@GRpcService
public class UserImpl extends UserServiceGrpc.UserServiceImplBase
{
  private final UserServiceImpl userService;
  private final TeamServiceImpl teamService;

  public UserImpl(UserServiceImpl userService, TeamServiceImpl teamService)
  {
    this.userService = userService;
    this.teamService = teamService;
  }

  @Override public void createUser(CreatingUser user, StreamObserver<User> responseObserver)
  {
    try
    {
      userService.addUser(new UserEntity(user.getFullName(), user.getEmail(),
          user.getUserName(), user.getPassword()));

      UserEntity userCreated = userService.getByUserName(user.getUserName());

      User user1 = userCreated.convertToUser();

      responseObserver.onNext(user1);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      Status status;
      if(e instanceof IllegalArgumentException) {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      } else {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
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
    UserEntity user = userService.getByEmail(email.getValue());

    if(user == null) {
      System.out.println("No user with this email has been found");
      responseObserver.onNext(null);
      responseObserver.onCompleted();
      return;
    }
    User user1 = user.convertToUser();
    responseObserver.onNext(user1);
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
