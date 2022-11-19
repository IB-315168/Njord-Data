package com.sep3yg9.njorddata.repos;

import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.TeamMemberId;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TeammemberRepository extends CrudRepository<TeamMember, TeamMemberId>
{
  @Transactional void deleteById_MemberId(int idmember);
}
