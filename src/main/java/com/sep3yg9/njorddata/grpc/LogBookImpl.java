package com.sep3yg9.njorddata.grpc;

import com.google.protobuf.Int32Value;
import com.sep3yg9.njorddata.grpc.protobuf.logbook.CreatingLogBook;
import com.sep3yg9.njorddata.grpc.protobuf.logbook.LogBookGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.logbook.LogBookServiceGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.logbook.UpdatingLogBook;
import com.sep3yg9.njorddata.models.LogbookEntity;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.services.interfaces.LogBookService;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService public class LogBookImpl
    extends LogBookServiceGrpc.LogBookServiceImplBase
{
  private final LogBookService logBookService;
  private final ProjectService projectService;

  public LogBookImpl(LogBookService logBookService,
      ProjectService projectService)
  {
    this.logBookService = logBookService;
    this.projectService = projectService;
  }

  @Override public void createLogBook(CreatingLogBook logBook,
      StreamObserver<LogBookGrpc> responseObserver)
  {
    try
    {
      ProjectEntity projectEntity = projectService.getById(
          logBook.getProjectassigned());

      LogbookEntity logbookCreated = logBookService.addLogBook(
          new LogbookEntity(projectEntity));

      LogBookGrpc logbook1 = logbookCreated.convertToLogBookGrpc();

      responseObserver.onNext(logbook1);
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

  @Override public void updateLogBook(UpdatingLogBook logBook,
      StreamObserver<LogBookGrpc> responseObserver)
  {
    try
    {
      logBookService.updateLogBook(logBook);

      LogBookGrpc logbook1 = logBookService.getById(logBook.getId())
          .convertToLogBookGrpc();
      responseObserver.onNext(logbook1);
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
      StreamObserver<LogBookGrpc> responseObserver)
  {
    try
    {
      LogbookEntity logbook = logBookService.getById(id.getValue());
      LogBookGrpc logbook1 = logbook.convertToLogBookGrpc();

      responseObserver.onNext(logbook1);
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

  @Override public void getByProjectId(Int32Value request,
      StreamObserver<LogBookGrpc> responseObserver)
  {
    try
    {
      LogbookEntity logbook = logBookService.getByProjectId(request.getValue());
      LogBookGrpc logBookGrpc = logbook.convertToLogBookGrpc();

      responseObserver.onNext(logBookGrpc);
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
