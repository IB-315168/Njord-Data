package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.meeting.UpdatingMeeting;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.models.TeamEntity;

public interface MeetingService
{
    void addMeeting(MeetingEntity meetingEntityRecord);
    void updateMeeting(UpdatingMeeting meeting);
    void removeMeeting(int id);
    MeetingEntity getById(int id);
}
