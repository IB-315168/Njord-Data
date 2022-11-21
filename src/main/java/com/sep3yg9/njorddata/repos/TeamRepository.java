package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<TeamEntity, Integer>
{
    TeamEntity findByIdteam(int id);
    TeamEntity findByName(String name);
    List<TeamEntity> findByTeamleader(UserEntity entity);
}
