package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.member.MemberGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.UpdatingMember;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.repos.MemberRepository;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service public class MemberServiceImpl implements MemberService
{
  private final MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository)
  {
    this.memberRepository = memberRepository;
  }

  @Override public List<MemberGrpc> getAllMembers()
  {
    List<MemberGrpc> userEntityRecords = new ArrayList<>();
    memberRepository.findAll().forEach(
        memberEntity -> userEntityRecords.add(memberEntity.convertToMemberGrpc()));
    return userEntityRecords;
  }

  @Override public void addMember(MemberEntity memberEntityRecord)
  {
    if (memberRepository.findByEmail(memberEntityRecord.getEmail()) != null)
    {
      throw new IllegalArgumentException("Email address already in use");
    }

    if (memberRepository.findByUsername(memberEntityRecord.getUserName()) != null)
    {
      throw new IllegalArgumentException("Username already in use");
    }

    memberRepository.save(memberEntityRecord);
  }

  @Override public void updateMember(UpdatingMember member)
  {
    MemberEntity memberEntity = getById(member.getId());

    if (!member.getEmail().isEmpty() && !member.getEmail()
        .equals(memberEntity.getEmail()))
    {
      memberEntity.setEmail(member.getEmail());
    }

    if (!member.getUserName().isEmpty() && !member.getUserName()
        .equals(memberEntity.getUserName()))
    {
      memberEntity.setUserName(member.getUserName());
    }
    if (!member.getPassword().isEmpty() && !member.getPassword()
        .equals(memberEntity.getPassword()))
    {
      memberEntity.setPassword(member.getPassword());
    }

    memberRepository.save(memberEntity);
  }

  @Override public void deleteMember(int id)
  {
    getById(id);
    memberRepository.deleteById(id);
  }

  public MemberEntity getById(int id)
  {
    MemberEntity memberEntity = memberRepository.findById(id);

    if (memberEntity == null)
    {
      throw new IllegalArgumentException("Member not found");
    }

    return memberEntity;
  }

  public MemberEntity getByUserName(String username)
  {
    MemberEntity memberEntity = memberRepository.findByUsername(username);

    if(memberEntity == null) {
      throw new IllegalArgumentException("Member not found");
    }

    return memberRepository.findByUsername(username);
  }

  public MemberEntity getByEmail(String email)
  {
    MemberEntity memberEntity = memberRepository.findByEmail(email);

    if(memberEntity == null) {
      throw new IllegalArgumentException("Member not found");
    }

    return memberRepository.findByEmail(email);
  }

  public Iterable<MemberEntity> getAll()
  {
    return memberRepository.findAll();
  }
}