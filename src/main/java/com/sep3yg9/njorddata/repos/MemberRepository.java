package com.sep3yg9.njorddata.repos;

import org.springframework.data.repository.CrudRepository;

import com.sep3yg9.njorddata.models.MemberEntity;

public interface MemberRepository extends CrudRepository<MemberEntity, Integer>
{
  MemberEntity findByUsername(String UserName);
  MemberEntity findByEmail(String Email);
}
