package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.task.*;
import com.sep3yg9.njorddata.models.*;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import com.sep3yg9.njorddata.services.interfaces.TaskService;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;

@GRpcService public class TaskImpl extends TaskServiceGrpc.TaskServiceImplBase {

    private final TaskService taskService;
    private final MemberService memberService;

    private final ProjectService projectService;

    public TaskImpl(TaskService taskService, MemberService memberService, ProjectService projectService) {
        this.taskService = taskService;
        this.memberService = memberService;
        this.projectService = projectService;
    }

    //needs fix
    @Override public void createTask(CreatingTask task, StreamObserver<TaskGrpc> responseObserver){
        try
        {
            ProjectEntity project = projectService.getById(task.getProjectAssigned());
            TaskEntity taskToCreate = new TaskEntity(project, task.getTitle(), task.getDescription(),
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

    @Override public void getByProjectId(Int32Value request, StreamObserver<GrpcTaskList> responseObserver){
        try
        {
            taskService.getByProjectId(request.getValue());
            List<TaskEntity> tasks = new ArrayList<>();
            for (TaskEntity task : taskService.getByProjectId(request.getValue())){
                tasks.add(task);
            }
            //how do I add here?? using which addAll() method
            GrpcTaskList list = GrpcTaskList.newBuilder().build();

            responseObserver.onNext(list);
            responseObserver.onCompleted();

        }
        catch (Exception e)
        {
            Status status;
            if (e instanceof IllegalArgumentException)
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
//    @Override public void getByProjectId(Int32Value request, StreamObserver<BasicTaskList> responseObserver)
//    {
//        try
//        {
//            taskService.getByProjectId(request.getValue());
//            List<BasicTask> tasks = new ArrayList<>();
//            for(TaskEntity task : taskService.getByProjectId(request.getValue())){
//                tasks.add(task.convertToBasicTask());
//            }
//            BasicTaskList list = BasicTaskList.newBuilder().addAllTasks(tasks).build();
//
//            responseObserver.onNext(list);
//            responseObserver.onCompleted();
//        }
//        catch (Exception e)
//        {
//            Status status;
//            if (e instanceof IllegalArgumentException)
//            {
//                status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
//            }
//            else
//            {
//                status = Status.INTERNAL.withDescription(e.getMessage());
//            }
//            responseObserver.onError(status.asRuntimeException());
//        }
//    }
}
