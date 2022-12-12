package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.member.MemberAvailabilityGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.MemberGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.UpdatingMember;
import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import com.sep3yg9.njorddata.models.MemberEntity;
import com.sep3yg9.njorddata.models.Memberavailability;
import com.sep3yg9.njorddata.models.RequirementEntity;
import com.sep3yg9.njorddata.models.SpecificDateTimeConverter;
import com.sep3yg9.njorddata.repos.MemberRepository;
import com.sep3yg9.njorddata.repos.MemberavailabilityRepository;
import com.sep3yg9.njorddata.services.interfaces.MemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service public class MemberServiceImpl implements MemberService
{
  private final MemberRepository memberRepository;
  private final MemberavailabilityRepository memberavailabilityRepository;

  public MemberServiceImpl(MemberRepository memberRepository, MemberavailabilityRepository memberavailabilityRepository)
  {
    this.memberRepository = memberRepository;
    this.memberavailabilityRepository = memberavailabilityRepository;
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

    memberEntity.setMemberavailabilities(new LinkedHashSet<>());
    memberRepository.save(memberEntity);

    for (MemberAvailabilityGrpc memberavailability : member.getAvailabilityList())
    {
      Memberavailability memberavailability1 = new Memberavailability(memberEntity,
          memberavailability.getDayofweek(), SpecificDateTimeConverter.convertToLocalTime(memberavailability.getStarthour()),
          SpecificDateTimeConverter.convertToLocalTime(memberavailability.getEndhour()));
      memberEntity.addAvailability(memberavailability1);

      memberavailabilityRepository.save(memberavailability1);
    }

    memberRepository.save(memberEntity);
  }

  @Override public void deleteMember(int id)
  {
    getById(id);
    memberRepository.deleteById(id);
  }

  @Override public MemberEntity getById(int id)
  {
    Optional<MemberEntity> memberEntity = memberRepository.findById(id);

    if (memberEntity.isEmpty())
    {
      throw new IllegalArgumentException("Member not found");
    }

    return memberEntity.get();
  }

  @Override public MemberEntity getByUserName(String username)
  {
    MemberEntity memberEntity = memberRepository.findByUsername(username);

    return memberRepository.findByUsername(username);
  }

  @Override public MemberEntity getByEmail(String email)
  {
    MemberEntity memberEntity = memberRepository.findByEmail(email);

    return memberRepository.findByEmail(email);
  }

  @Override public Iterable<MemberEntity> getAll()
  {
    return memberRepository.findAll();
  }
}
