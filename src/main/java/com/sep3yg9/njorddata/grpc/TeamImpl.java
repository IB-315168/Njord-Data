package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.team.CreatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.services.interfaces.TeamService;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;

@GRpcService public class TeamImpl extends TeamServiceGrpc.TeamServiceImplBase
{
  private final TeamService teamService;
  private final MemberService memberService;

  public TeamImpl(TeamService teamService, MemberService memberService)
  {
    this.teamService = teamService;
    this.memberService = memberService;
  }

  @Override public void createTeam(CreatingTeam team,
      StreamObserver<Team> responseObserver)
  {
    try
    {
      MemberEntity user = memberService.getById(team.getTeamLeaderId());

      teamService.addTeam(
          new TeamEntity(user, team.getName()));

      TeamEntity teamCreated = teamService.getByName(team.getName());

      Team team1 = Team.newBuilder().setId(teamCreated.getIdTeam())
          .setTeamLeader(user.convertToUser()).setName(teamCreated.getName()).build();

      responseObserver.onNext(team1);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      Status status;
      if (e instanceof IllegalArgumentException)
      {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      }
      else
      {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
  }

  @Override public void getById(Int32Value id,
      StreamObserver<Team> responseObserver)
  {
    try
    {
      TeamEntity team = teamService.getById(id.getValue());
      List<TeamMember> members = team.getMembers();

      List<User> users = new ArrayList<>();
      for (TeamMember member : members)
      {
        users.add(member.getUserEntity().convertToUser());
      }

      Team team1 = Team.newBuilder().setId(team.getIdTeam())
          .setName(team.getName()).setTeamLeader(team.getTeamLeader().convertToUser())
          .addAllMembers(users).build();

      responseObserver.onNext(team1);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      Status status;
      if (e instanceof IllegalArgumentException)
      {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      }
      else
      {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
  }

  @Override public void deleteTeam(Int32Value id,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      teamService.removeTeam(id.getValue());
      responseObserver.onNext(Empty.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      Status status;
      if (e instanceof IllegalArgumentException)
      {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      }
      else
      {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
  }

  @Override public void updateTeam(UpdatingTeam team,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      teamService.updateTeam(team);
      responseObserver.onNext(Empty.newBuilder().build());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      Status status;
      if (e instanceof IllegalArgumentException)
      {
        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
      }
      else
      {
        status = Status.INTERNAL.withDescription(e.getMessage());
      }
      responseObserver.onError(status.asRuntimeException());
    }
  }
}
