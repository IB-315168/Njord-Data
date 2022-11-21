package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.project.*;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.SpecificTimeConverter;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import com.sep3yg9.njorddata.services.interfaces.TeamService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@GRpcService public class ProjectImpl
    extends ProjectServiceGrpc.ProjectServiceImplBase
{

  private final ProjectService projectService;
  private final TeamService teamService;

  public ProjectImpl(ProjectService projectService, TeamService teamService)
  {
    this.projectService = projectService;
    this.teamService = teamService;
  }

  @Override public void createProject(CreatingProject project,
      StreamObserver<Project> responseObserver)
  {
    try
    {
      TeamEntity teamEntity = teamService.getById(project.getTeamId());

      ProjectEntity projectCreated = projectService.addProject(
          new ProjectEntity(teamEntity, project.getName(),
              LocalDateTime.now(), SpecificTimeConverter.convertToLocalDateTime(project.getDeadline())));

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
