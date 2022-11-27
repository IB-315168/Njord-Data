package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.task.CreatingTask;
import com.sep3yg9.njorddata.grpc.protobuf.task.TaskGrpc;
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
    @Override public void createTask(CreatingTask task, StreamObserver<TaskGrpc> responseObserver){
        try
        {
            TaskEntity taskToCreate = new TaskEntity(task.getTitle(), task.getDescription(),
                    task.getStatus().charAt(0), SpecificDateTimeConverter.convertToLocalTime(task.getTimeestimation()),
                    SpecificDateTimeConverter.convertToLocalDateTime(task.getCreationdate()));

            if(task.getMemberassigned() != 0) {
                MemberEntity memberEntity = memberService.getById(task.getMemberassigned());
                taskToCreate.setMemberassigned(memberEntity);
            }

            TaskEntity taskCreated = taskService.addTask(taskToCreate);

            TaskGrpc task1 = taskCreated.convertToTaskGrpc();
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

    @Override public void updateTask(UpdatingTask task, StreamObserver<TaskGrpc> responseObserver){
        try{
            taskService.updateTask(task);

            TaskGrpc task1 = taskService.getById(task.getId()).convertToTaskGrpc();
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

    @Override public void getById(Int32Value id, StreamObserver<TaskGrpc> responseObserver)
    {
        try {
            TaskEntity task = taskService.getById(id.getValue());

            TaskGrpc task1 = task.convertToTaskGrpc();

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
