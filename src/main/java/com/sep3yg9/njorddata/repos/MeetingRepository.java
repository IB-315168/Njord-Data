package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.MeetingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingRepository extends CrudRepository <MeetingEntity, Integer>
{
    MeetingEntity findByIdmeeting(int id);
    List<MeetingEntity> findByAssignedproject_Idproject(int idproject);

}
