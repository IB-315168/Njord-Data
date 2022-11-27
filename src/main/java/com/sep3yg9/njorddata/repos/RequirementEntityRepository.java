package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.RequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementEntityRepository
    extends JpaRepository<RequirementEntity, Integer>
{
}