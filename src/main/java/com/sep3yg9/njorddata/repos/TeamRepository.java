package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.TeamEntity;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamEntity, Integer>
{
    TeamEntity findByIdteam(int id);
    TeamEntity findByName(String name);
}
