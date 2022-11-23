package com.sep3yg9.njorddata.grpc;

import com.sep3yg9.njorddata.grpc.protobuf.project.ProjectServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.task.CreatingTask;
import com.sep3yg9.njorddata.grpc.protobuf.task.Task;
import com.sep3yg9.njorddata.grpc.protobuf.task.TaskServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.task.UpdatingTask;
import com.sep3yg9.njorddata.models.*;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import com.sep3yg9.njorddata.services.interfaces.TaskService;
import com.sep3yg9.njorddata.services.interfaces.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.time.LocalDateTime;

@GRpcService public class TaskImpl extends TaskServiceGrpc.TaskServiceImplBase {

    private final TaskService taskService;
    private final UserService userService;

    public TaskImpl(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    //needs fix
    @Override public void createTask(CreatingTask task, StreamObserver<Task> responseObserver){
        try
        {
            UserEntity userEntity = userService.getById(task.getMemberassigned());

            TaskEntity taskCreated = taskService.addTask(
                    new TaskEntity(userEntity, task.getTitle(), task.getDescription(),
                            task.getStatus().charAt(0), SpecificHourConverter.convertToLocalDateTime(task.getTimeestimation()),
                            SpecificTimeConverter.convertToLocalDateTime(task.getCreationdate()))
            );

            Task task1 = taskCreated.convertToTask();
            responseObserver.onNext(task1);
            responseObserver.onCompleted();
        }
        catch (Exception e){
            Status status;
            if(e instanceof IllegalArgumentException)
            {
                status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            }
            else
            {
                status = Status.INTERNAL.withDescription(e.getMessage());
            }
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override public void updateTask(UpdatingTask task, StreamObserver<Task> responseObserver){
        try{
            taskService.updateTask(task);

            Task task1 = taskService.getById(task.getId()).convertToTask();
            responseObserver.onNext(task1);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            Status status;
            if(e instanceof IllegalArgumentException)
            {
                status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            }
            else
            {
                status = Status.INTERNAL.withDescription(e.getMessage());
            }
            responseObserver.onError(status.asRuntimeException());
        }
    }

}
