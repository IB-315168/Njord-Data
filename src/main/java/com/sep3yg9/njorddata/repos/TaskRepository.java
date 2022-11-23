package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer>
{
    TaskEntity findByIdtask(int ind);
}
