package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.project.*;
import com.sep3yg9.njorddata.models.LogbookEntity;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.services.interfaces.LogBookService;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import com.sep3yg9.njorddata.services.interfaces.TeamService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@GRpcService public class ProjectImpl
    extends ProjectServiceGrpc.ProjectServiceImplBase
{

  private final ProjectService projectService;
  private final TeamService teamService;
  private final LogBookService logBookService;

  public ProjectImpl(ProjectService projectService, TeamService teamService, LogBookService logBookService)
  {
    this.projectService = projectService;
    this.teamService = teamService;
    this.logBookService = logBookService;
  }

  @Override public void createProject(CreatingProject project,
      StreamObserver<ProjectGrpc> responseObserver)
  {
    try
    {
      TeamEntity teamEntity = teamService.getById(project.getTeamId());

      ProjectEntity projectCreated = projectService.addProject(
          new ProjectEntity(teamEntity, project.getName(), LocalDateTime.now(),
              SpecificDateTimeConverter.convertToLocalDateTime(project.getDeadline())));

      ProjectGrpc project1 = projectCreated.convertToProjectGrpc();

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
      StreamObserver<ProjectGrpc> responseObserver)
  {
    try
    {
      projectService.updateProject(project);

      ProjectGrpc project1 = projectService.getById(project.getId())
          .convertToProjectGrpc();
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
      StreamObserver<ProjectGrpc> responseObserver)
  {
    try
    {
      ProjectEntity project = projectService.getById(id.getValue());

      ProjectGrpc project1 = project.convertToProjectGrpc();

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

  @Override public void getByMemberId(Int32Value id,
      StreamObserver<BasicProjectList> responseObserver)
  {
    try
    {
      ArrayList<BasicProject> basicProjectsOfUser = new ArrayList<>();

      for(ProjectEntity project : projectService.getByUserId(id.getValue())) {
        basicProjectsOfUser.add(project.convertToBasicProject());
      }

      responseObserver.onNext(BasicProjectList.newBuilder()
          .addAllProjects(basicProjectsOfUser)
          .build());
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
