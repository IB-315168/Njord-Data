package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer>
{
    ProjectEntity findByIdproject(int id);
    List<ProjectEntity> findByTeamassigned_Idteam(int idteam);

}
