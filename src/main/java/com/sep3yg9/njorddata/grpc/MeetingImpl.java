package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.CreatingMeeting;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.MeetingGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.MeetingServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.UpdatingMeeting;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.services.interfaces.MeetingService;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class MeetingImpl extends MeetingServiceGrpc.MeetingServiceImplBase
{
  private final MeetingService meetingService;
  private final MemberService memberService;

  public MeetingImpl(MeetingService meetingService, MemberService memberService)
  {
    this.meetingService = meetingService;
    this.memberService = memberService;
  }

  @Override public void createMeeting(CreatingMeeting meeting,
      StreamObserver<MeetingGrpc> responseObserver)
  {
    try
    {
      MemberEntity memberEntity = memberService.getById(
          meeting.getAssignedleader());

      MeetingEntity meetingEntity = meetingService.addMeeting(
          new MeetingEntity(memberEntity, meeting.getTitle(),
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
}
