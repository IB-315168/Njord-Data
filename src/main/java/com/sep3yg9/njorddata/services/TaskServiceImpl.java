package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.task.UpdatingTask;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.models.TaskEntity;
import com.sep3yg9.njorddata.repos.TaskRepository;
import com.sep3yg9.njorddata.repos.MemberRepository;
import com.sep3yg9.njorddata.services.interfaces.TaskService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MemberRepository memberRepository) {
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
    }

    public TaskEntity addTask(TaskEntity taskEntityRecord) {
        return taskRepository.save(taskEntityRecord);
    }

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

            if (!task.getDescription().isEmpty() && !taskEntity.getDescription().equals(task.getDescription()))
            {
                taskEntity.setDescription(task.getDescription());
            }

            if(!task.getStatus().isEmpty() && !taskEntity.getStatus().equals(task.getStatus())) {
                taskEntity.setStatus(task.getStatus());
            }

            if(task.getTimeestimation().isInitialized()) {
                taskEntity.setTimeestimation(SpecificDateTimeConverter.convertToLocalTime(task.getTimeestimation()));
            }

            if(task.getMemberassigned() == 0) {
                taskEntity.setMemberassigned(null);
            } else {
                Optional<MemberEntity> memberEntity = memberRepository.findById(task.getMemberassigned());

                if(memberEntity.isEmpty()) {
                    throw new IllegalArgumentException("Member not found");
                }

                taskEntity.setMemberassigned(memberEntity.get());
            }

            taskRepository.save(taskEntity);
        }
    }

    public void removeTask(int id) {
        getById(id);
        taskRepository.deleteById(id);
    }

    public TaskEntity getById(int id) {
        TaskEntity taskEntity = taskRepository.findByIdtask(id);
        if(taskEntity == null)
        {
            throw new IllegalArgumentException("Task not found");
        }
        return taskRepository.findByIdtask(id);
    }

    @Override public List<TaskEntity> getByProjectId(int id){
        List<TaskEntity> taskEntities = taskRepository.findByAssignedproject_Idproject(id);

        return taskEntities;
    }
}
