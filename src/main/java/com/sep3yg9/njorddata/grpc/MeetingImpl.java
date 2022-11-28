package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.*;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.services.interfaces.MeetingService;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;

@GRpcService
public class MeetingImpl extends MeetingServiceGrpc.MeetingServiceImplBase
{
  private final MeetingService meetingService;
  private final MemberService memberService;
  private final ProjectService projectService;

  public MeetingImpl(MeetingService meetingService, MemberService memberService,
      ProjectService projectService)
  {
    this.meetingService = meetingService;
    this.memberService = memberService;
    this.projectService = projectService;
  }

  @Override public void createMeeting(CreatingMeeting meeting,
      StreamObserver<MeetingGrpc> responseObserver)
  {
    try
    {
      MemberEntity memberEntity = memberService.getById(
          meeting.getAssignedleader());

      ProjectEntity project = projectService.getById(
          meeting.getProjectAssigned());

      MeetingEntity meetingEntity = meetingService.addMeeting(
          new MeetingEntity(project, memberEntity, meeting.getTitle(),
              meeting.getDescription(),
              SpecificDateTimeConverter.convertToLocalDateTime(
                  meeting.getStartdate()),
              SpecificDateTimeConverter.convertToLocalDateTime(
                  meeting.getEnddate())));

      MeetingGrpc meeting1 = meetingEntity.convertToMeetingGrpc();

      responseObserver.onNext(meeting1);
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

  @Override public void updateMeeting(UpdatingMeeting meeting,
      StreamObserver<MeetingGrpc> responseObserver)
  {
    try
    {
      meetingService.updateMeeting(meeting);

      MeetingGrpc meeting1 = meetingService.getById(meeting.getId())
          .convertToMeetingGrpc();
      responseObserver.onNext(meeting1);
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

  @Override public void deleteMeeting(Int32Value id,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      meetingService.removeMeeting(id.getValue());
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

  @Override public void getById(Int32Value id,
      StreamObserver<MeetingGrpc> responseObserver)
  {
    try
    {
      MeetingEntity meeting = meetingService.getById(id.getValue());

      MeetingGrpc meeting2 = meeting.convertToMeetingGrpc();

      responseObserver.onNext(meeting2);
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

  @Override public void getByProjectId(Int32Value request,
      StreamObserver<GrpcMeetingList> responseObserver)
  {
    try
    {
      projectService.getById(request.getValue());

      List<MeetingGrpc> meetings = new ArrayList<>();

      for(MeetingEntity meeting : meetingService.getByProjectId(request.getValue())) {
        meetings.add(meeting.convertToMeetingGrpc());
      }

      GrpcMeetingList list = GrpcMeetingList.newBuilder().addAllMeetingsList(meetings).build();

      responseObserver.onNext(list);
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
