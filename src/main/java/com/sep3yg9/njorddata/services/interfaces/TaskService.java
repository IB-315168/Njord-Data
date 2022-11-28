package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.task.UpdatingTask;
import com.sep3yg9.njorddata.models.TaskEntity;

import java.util.List;

public interface TaskService
{
    TaskEntity addTask(TaskEntity taskEntity);
    void updateTask(UpdatingTask task);
    void removeTask(int id);
    TaskEntity getById(int id);
    List<TaskEntity> getByProjectId(int id);
}
