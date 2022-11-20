package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.sep3yg9.njorddata.grpc.protobuf.project.*;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.RequirementEntity;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@GRpcService
public class ProjectImpl extends ProjectServiceGrpc.ProjectServiceImplBase {

    private final ProjectService projectService;

    public ProjectImpl(ProjectService projectService) {
        this.projectService = projectService;
    }
    @Override
    public void createProject(CreatingProject project, StreamObserver<Project> responseObserver){
        try {
            if(projectService.getById(project.getTeamId()) != null) {//TODO review the id
                throw new IllegalArgumentException("Project Already exist");
            }

            LocalDateTime deadline = new Jsr310JpaConverters.LocalDateTimeConverter().convertToEntityAttribute(new Date(project.getDeadline().getYear(),project.getDeadline().getMonth(),project.getDeadline().getDay()));
            projectService.addProject(new ProjectEntity(project.getTeamId(),project.getName(), LocalDateTime.now(),deadline,requirement));

            ProjectEntity projectCreated = projectService.getById(project.getTeamId());

            Project project1 = projectCreated.convertToProject();
            responseObserver.onNext(project1);
            responseObserver.onCompleted();
        }
        catch (Exception e)
        {
            Status status;
            if(e instanceof IllegalArgumentException) {
                status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            } else {
                status = Status.INTERNAL.withDescription(e.getMessage());
            }
            responseObserver.onError(status.asRuntimeException());
        }
    }
    @Override
    public void updateProject(UpdatingProject project,StreamObserver<Project> responseObserver){
        projectService.updateProject(project);
        responseObserver.onNext(Project.newBuilder().build());
        responseObserver.onCompleted();
    }
    @Override
    public void deleteProject(Int32Value id,StreamObserver<Project> responseObserver){
        projectService.removeProject(id.getValue());
    }
    @Override
    public void getById(Int32Value id, StreamObserver<Project> responseObserver){
        ProjectEntity project = projectService.getById(id.getValue());

        Project project1 = Project.newBuilder()
                .setId(project.getIdproject())
                .setName(project.getName())
                .setTeamId(project.getTeamAssigned())
                .setDeadline(SpecificTime.newBuilder().setDay(project.getDeadline().getDayOfMonth()).setMonth(project.getDeadline().getMonth().getValue()).setYear(project.getDeadline().getYear()).build())
                .setRequirements(project.getRequirements())
                .build();

        responseObserver.onNext(project1);
        responseObserver.onCompleted();
    }
}
