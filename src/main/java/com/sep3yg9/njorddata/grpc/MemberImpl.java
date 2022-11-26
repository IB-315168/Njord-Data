package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.member.*;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.services.TeamServiceImpl;
import com.sep3yg9.njorddata.services.MemberServiceImpl;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;

@GRpcService public class MemberImpl extends MemberServiceGrpc.MemberServiceImplBase
{
  private final MemberServiceImpl memberService;
  private final TeamServiceImpl teamService;

  public MemberImpl(MemberServiceImpl memberService, TeamServiceImpl teamService)
  {
    this.memberService = memberService;
    this.teamService = teamService;
  }

  @Override public void createMember(CreatingMember member,
      StreamObserver<MemberGrpc> responseObserver)
  {
    try
    {
      memberService.addMember(new MemberEntity(member.getFullName(), member.getEmail(),
          member.getUserName(), member.getPassword()));

      MemberEntity userCreated = memberService.getByUserName(member.getUserName());

      MemberGrpc member1 = userCreated.convertToMemberGrpc();

      responseObserver.onNext(member1);
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

  @Override public void updateMember(UpdatingMember member,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      memberService.updateMember(member);
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

  @Override public void deleteMember(Int32Value id,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      memberService.deleteMember(id.getValue());
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
      StreamObserver<MemberGrpc> responseObserver)
  {
    try
    {
      MemberEntity member = memberService.getById(id.getValue());

      ArrayList<BasicTeam> teamMembership = new ArrayList<>();
      for (TeamMember teamMember : member.getTeams())
      {
        teamMembership.add(teamMember.getTeamEntity().convertToBasicTeam());
      }

      ArrayList<BasicTeam> teamsLeaders = new ArrayList<>();
      for (TeamEntity teamEntity : teamService.getByTeamLeaderId(
          member.getIdmember()))
      {
        teamsLeaders.add(teamEntity.convertToBasicTeam());
      }

      MemberGrpc memberGrpc = MemberGrpc.newBuilder().setId(member.getIdmember())
          .setFullName(member.getFullName()).setEmail(member.getEmail())
          .setUserName(member.getUserName()).setPassword(member.getPassword())
          .addAllMemberTeams(teamMembership).addAllMemberTeams(teamsLeaders)
          .build();

      responseObserver.onNext(memberGrpc);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
//      Status status;
//      if (e instanceof IllegalArgumentException)
//      {
//        status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
//      }
//      else
//      {
//        status = Status.INTERNAL.withDescription(e.getMessage());
//      }
//      responseObserver.onError(status.asRuntimeException());
      throw e;
    }
  }

  @Override public void getByEmail(com.google.protobuf.StringValue email,
      StreamObserver<MemberGrpc> responseObserver)
  {
    try
    {
      MemberEntity user = memberService.getByEmail(email.getValue());

      MemberGrpc user1 = user.convertToMemberGrpc();
      responseObserver.onNext(user1);
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

  @Override public void searchMember(SearchingMember query,
      StreamObserver<MemberList> responseObserver)
  {
    ArrayList<MemberGrpc> memberList = new ArrayList<>(memberService.getAllMembers());

    if (!query.getFullName().isEmpty())
    {
      memberList.removeIf(member -> !member.getFullName().toLowerCase()
          .contains(query.getFullName().toLowerCase()));
    }

    if (!query.getUserName().isEmpty())
    {
      memberList.removeIf(member -> !member.getUserName().toLowerCase()
          .contains(query.getUserName().toLowerCase()));
    }

    if (!query.getEmail().isEmpty())
    {
      memberList.removeIf(member -> !member.getEmail().toLowerCase()
          .contains(query.getEmail().toLowerCase()));
    }

    MemberList searchResults = MemberList.newBuilder().addAllMember(memberList).build();

    responseObserver.onNext(searchResults);
    responseObserver.onCompleted();
  }
}
