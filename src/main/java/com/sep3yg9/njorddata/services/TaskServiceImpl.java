package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.task.UpdatingTask;
import com.sep3yg9.njorddata.models.TaskEntity;
import com.sep3yg9.njorddata.repos.TaskRepository;
import com.sep3yg9.njorddata.repos.UserRepository;
import com.sep3yg9.njorddata.services.interfaces.TaskService;
import org.springframework.stereotype.Service;

@Service public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskEntity addTask(TaskEntity taskEntityRecord) {
        return taskRepository.save(taskEntityRecord);
    }

    @Override
    public void updateTask(UpdatingTask task) {

    }

    @Override
    public void removeTask(int id) {

    }

    @Override
    public TaskEntity getById(int id) {
        return null;
    }
}
