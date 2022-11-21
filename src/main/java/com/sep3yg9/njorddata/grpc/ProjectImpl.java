package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.sep3yg9.njorddata.grpc.protobuf.project.*;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.RequirementEntity;
import com.sep3yg9.njorddata.models.SpecifcTimeConverter;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@GRpcService public class ProjectImpl
    extends ProjectServiceGrpc.ProjectServiceImplBase
{

  private final ProjectService projectService;

  public ProjectImpl(ProjectService projectService)
  {
    this.projectService = projectService;
  }

  @Override public void createProject(CreatingProject project,
      StreamObserver<Project> responseObserver)
  {
    try
    {
      LocalDateTime deadline = LocalDateTime.of(
          LocalDate.of(project.getDeadline().getYear(),
              project.getDeadline().getMonth(), project.getDeadline().getDay()),
          LocalTime.of(project.getDeadline().getHour(),
              project.getDeadline().getMinute()));

      projectService.addProject(
          new ProjectEntity(project.getTeamId(), project.getName(),
              LocalDateTime.now(), deadline));

      ProjectEntity projectCreated = projectService.getById(
          project.getTeamId());

      Project project1 = projectCreated.convertToProject();

      responseObserver.onNext(project1);
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

  @Override public void updateProject(UpdatingProject project,
      StreamObserver<Project> responseObserver)
  {
    try
    {
      projectService.updateProject(project);

      Project project1 = projectService.getById(project.getId())
          .convertToProject();
      responseObserver.onNext(project1);
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

  @Override public void deleteProject(Int32Value id,
      StreamObserver<Empty> responseObserver)
  {
    try
    {
      projectService.removeProject(id.getValue());

      responseObserver.onNext(Empty.newBuilder().build());
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

  @Override public void getById(Int32Value id,
      StreamObserver<Project> responseObserver)
  {
    try
    {
      ProjectEntity project = projectService.getById(id.getValue());

      Project project1 = project.convertToProject();

      responseObserver.onNext(project1);
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
}