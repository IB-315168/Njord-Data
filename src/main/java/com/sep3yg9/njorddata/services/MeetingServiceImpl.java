package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.meeting.UpdatingMeeting;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.repos.MeetingRepository;
import com.sep3yg9.njorddata.repos.MemberRepository;
import com.sep3yg9.njorddata.services.interfaces.MeetingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service public class MeetingServiceImpl implements MeetingService
{

  private final MemberRepository memberRepository;
  private final MeetingRepository meetingRepository;

  public MeetingServiceImpl(MemberRepository memberRepository,
      MeetingRepository meetingRepository)
  {
    this.memberRepository = memberRepository;
    this.meetingRepository = meetingRepository;
  }

  @Override public MeetingEntity addMeeting(MeetingEntity meetingEntityRecord)
  {
    return meetingRepository.save(meetingEntityRecord);
  }

  @Override public void updateMeeting(UpdatingMeeting meeting)
  {
    MeetingEntity meetingEntity = getById(meeting.getId());

    if (!meetingEntity.getTitle().isEmpty() && !meeting.getTitle()
        .equals(meetingEntity.getTitle()))
    {
      meetingEntity.setTitle(meeting.getTitle());
    }
    if (!meetingEntity.getDescription().isEmpty() && !meeting.getDescription()
        .equals(meetingEntity.getDescription()))
    {
      meetingEntity.setDescription(meeting.getDescription());
    }

    if (meetingEntity.getStartdatetime().compareTo(SpecificDateTimeConverter.convertToLocalDateTime(meeting.getStartdate())) != 0)
    {
      meetingEntity.setStartdatetime(
          SpecificDateTimeConverter.convertToLocalDateTime(meeting.getStartdate()));
    }
    if (meetingEntity.getEnddatetime().compareTo(SpecificDateTimeConverter.convertToLocalDateTime(meeting.getEnddate())) != 0)
    {
      meetingEntity.setEnddatetime(
          SpecificDateTimeConverter.convertToLocalDateTime(meeting.getEnddate()));
    }

    meetingRepository.save(meetingEntity);
  }

  @Override public void removeMeeting(int id)
  {
    getById(id);
    meetingRepository.deleteById(id);
  }

  @Override public MeetingEntity getById(int id)
  {
    MeetingEntity meetingEntity = meetingRepository.findByIdmeeting(id);

    if (meetingEntity == null)
    {
      throw new IllegalArgumentException("Meeting not found!");
    }

    return meetingEntity;
  }

  @Override public List<MeetingEntity> getByProjectId(int id) {

    List<MeetingEntity> meetingEntities = meetingRepository.findByAssignedproject_Idproject(id);

    return meetingEntities;
  }
}
