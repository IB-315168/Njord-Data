package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.task.CreatingTask;
import com.sep3yg9.njorddata.grpc.protobuf.task.Task;
import com.sep3yg9.njorddata.grpc.protobuf.task.TaskServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.task.UpdatingTask;
import com.sep3yg9.njorddata.models.*;
import com.sep3yg9.njorddata.services.interfaces.TaskService;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService public class TaskImpl extends TaskServiceGrpc.TaskServiceImplBase {

    private final TaskService taskService;
    private final MemberService memberService;

    public TaskImpl(TaskService taskService, MemberService memberService) {
        this.taskService = taskService;
        this.memberService = memberService;
    }

    //needs fix
    @Override public void createTask(CreatingTask task, StreamObserver<Task> responseObserver){
        try
        {
            MemberEntity memberEntity = memberService.getById(task.getMemberassigned());

            TaskEntity taskCreated = taskService.addTask(
                    new TaskEntity(memberEntity, task.getTitle(), task.getDescription(),
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

    @Override public void deleteTask(Int32Value id, StreamObserver<Empty> responseObserver)
    {
        try
        {
            taskService.removeTask(id.getValue());

            responseObserver.onNext(Empty.newBuilder().build());
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

    @Override public void getById(Int32Value id, StreamObserver<Task> responseObserver)
    {
        try {
            TaskEntity task = taskService.getById(id.getValue());

            Task task1 = task.convertToTask();

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
