package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.meeting.UpdatingMeeting;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.SpecificTimeConverter;
import com.sep3yg9.njorddata.repos.MeetingRepository;
import com.sep3yg9.njorddata.repos.UserRepository;
import com.sep3yg9.njorddata.services.interfaces.MeetingService;
import org.springframework.stereotype.Service;

@Service public class MeetingServiceImpl implements MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private SpecificTimeConverter specificTimeConverter;

    public MeetingServiceImpl(UserRepository userRepository, MeetingRepository meetingRepository) {
        this.userRepository = userRepository;
        this.meetingRepository = meetingRepository;
        specificTimeConverter=new SpecificTimeConverter();
    }

    @Override
    public MeetingEntity addMeeting(MeetingEntity meetingEntityRecord) {
        return meetingRepository.save(meetingEntityRecord);
    }

    @Override
    public void updateMeeting(UpdatingMeeting meeting) {
        MeetingEntity meetingEntity=getById(meeting.getId());

        if(!meetingEntity.getTitle().isEmpty() && !meeting.getTitle().equals(meetingEntity.getTitle()))
        {
            meetingEntity.setTitle(meeting.getTitle());
        }
        if(!meetingEntity.getDescription().isEmpty() && !meeting.getDescription().equals(meetingEntity.getDescription()))
        {
            meetingEntity.setDescription(meeting.getDescription());
        }
        if(!meeting.getStartdate().equals(meetingEntity.getStartdatetime()))
        {
            meetingEntity.setStartdatetime(specificTimeConverter.convertToLocalDateTime(meeting.getStartdate()));
        }
        if(!meeting.getEnddate().equals(meetingEntity.getEnddatetime()))
        {
            meetingEntity.setEnddatetime(specificTimeConverter.convertToLocalDateTime(meeting.getEnddate()));
        }

        meetingRepository.save(meetingEntity);
    }

    @Override
    public void removeMeeting(int id) {
        getById(id);
        meetingRepository.deleteById(id);
    }

    @Override
    public MeetingEntity getById(int id) {
        MeetingEntity meetingEntity=meetingRepository.findByIdmeeting(id);

        if(meetingEntity==null)
        {
            throw new IllegalArgumentException("Meeting not found!");
        }

        return meetingEntity;
    }
}
