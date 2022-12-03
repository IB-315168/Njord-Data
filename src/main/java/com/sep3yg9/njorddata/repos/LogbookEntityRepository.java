package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.LogbookEntity;
import org.springframework.data.repository.CrudRepository;

public interface LogbookEntityRepository
    extends CrudRepository<LogbookEntity, Integer>
{
    LogbookEntity findById(int id);
    LogbookEntity findByAssignedproject_Idproject(int idproject);
}