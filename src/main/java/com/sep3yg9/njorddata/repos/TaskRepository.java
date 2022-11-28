package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer>
{
    TaskEntity findByIdtask(int ind);
    List<TaskEntity> findByAssignedproject_Idproject(int idproject);
}
