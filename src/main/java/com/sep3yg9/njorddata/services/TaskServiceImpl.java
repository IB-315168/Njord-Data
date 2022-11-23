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
        TaskEntity taskEntity = taskRepository.findByIdtask(task.getId());

        if (taskEntity == null){
            throw new IllegalArgumentException("Task not found");
        }else
        {
            if (!task.getTitle().isEmpty() && !taskEntity.getTitle().equals(task.getTitle()))
            {
                taskEntity.setTitle(task.getTitle());
            }
            if (!taskEntity.getDescription().isEmpty() && !taskEntity.getDescription().equals(task.getDescription()))
            {
                taskEntity.setDescription(task.getDescription());
            }
            //what can be updated?

            taskRepository.save(taskEntity);
        }
    }

    @Override
    public void removeTask(int id) {
        getById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskEntity getById(int id) {
        TaskEntity taskEntity = taskRepository.findByIdtask(id);
        if(taskEntity == null)
        {
            throw new IllegalArgumentException("Task not found");
        }
        return taskRepository.findByIdtask(id);
    }
}
