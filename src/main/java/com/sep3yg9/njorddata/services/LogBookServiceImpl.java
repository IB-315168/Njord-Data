package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.logbook.LogBookEntryGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.logbook.UpdatingLogBook;
import com.sep3yg9.njorddata.models.LogbookEntity;
import com.sep3yg9.njorddata.models.LogbookentryEntity;
import com.sep3yg9.njorddata.models.MeetingEntity;
import com.sep3yg9.njorddata.repos.LogbookEntityRepository;
import com.sep3yg9.njorddata.repos.LogbookentryEntityRepository;
import com.sep3yg9.njorddata.repos.MeetingRepository;
import com.sep3yg9.njorddata.repos.ProjectRepository;
import com.sep3yg9.njorddata.services.interfaces.LogBookService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service public class LogBookServiceImpl implements LogBookService
{

  private final ProjectRepository projectRepository;
  private final MeetingRepository meetingRepository;
  private final LogbookEntityRepository LogBookEntityRepo;
  private final LogbookentryEntityRepository LogBookEntryEntityRepo;

  public LogBookServiceImpl(ProjectRepository projectRepository,
      MeetingRepository meetingRepository,
      LogbookEntityRepository logBookEntityRepo,
      LogbookentryEntityRepository logBookEntryEntityRepo)
  {
    this.projectRepository = projectRepository;
    this.meetingRepository = meetingRepository;
    LogBookEntityRepo = logBookEntityRepo;
    LogBookEntryEntityRepo = logBookEntryEntityRepo;
  }

  @Override public LogbookEntity addLogBook(LogbookEntity logbookEntity)
  {
    return LogBookEntityRepo.save(logbookEntity);
  }

  @Override public void updateLogBook(UpdatingLogBook logBook)
  {
    LogbookEntity logbookEntity = LogBookEntityRepo.findById(logBook.getId());

    if (logbookEntity == null)
    {
      throw new IllegalArgumentException("Logbook not found");
    }
    logbookEntity.setLogbookentries(new LinkedHashSet<>());
    LogBookEntityRepo.save(logbookEntity);
    for (LogBookEntryGrpc entry : logBook.getLogbookentriesList())
    {
      MeetingEntity meeting = meetingRepository.findByIdmeeting(
          entry.getAssignedmeeting());
      LogbookentryEntity entity = new LogbookentryEntity(logbookEntity, meeting,
          entry.getContents());
      logbookEntity.addEntry(entity);
      LogBookEntryEntityRepo.save(entity);
    }
    LogBookEntityRepo.save(logbookEntity);
  }

  @Override public void removeLogBook(int id)
  {
    getById(id);
    LogBookEntityRepo.deleteById(id);
  }

  @Override public LogbookEntity getById(int id)
  {
    LogbookEntity logbookEntity = LogBookEntityRepo.findById(id);
    if (logbookEntity == null)
    {
      throw new IllegalArgumentException("Logbook not found");
    }
    return LogBookEntityRepo.findById(id);
  }

  @Override public LogbookEntity getByProjectId(int id)
  {
    LogbookEntity logbookEntity = LogBookEntityRepo.findByAssignedproject_Idproject(
        id);
    if (logbookEntity == null)
    {
      throw new IllegalArgumentException("Logbook not found");
    }
    return LogBookEntityRepo.findByAssignedproject_Idproject(id);
  }
}
