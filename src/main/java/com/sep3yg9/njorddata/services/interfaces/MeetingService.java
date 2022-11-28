package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.meeting.UpdatingMeeting;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.TeamEntity;

import java.util.List;

public interface MeetingService
{
    MeetingEntity addMeeting(MeetingEntity meetingEntityRecord);
    void updateMeeting(UpdatingMeeting meeting);
    void removeMeeting(int id);
    MeetingEntity getById(int id);
    List<MeetingEntity> getByProjectId(int id);
}
