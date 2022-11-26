package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser;
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

  public List<User> getAllUsers()
  {
    List<User> userEntityRecords = new ArrayList<>();
    memberRepository.findAll().forEach(
        userEntity -> userEntityRecords.add(userEntity.convertToUser()));
    return userEntityRecords;
  }

  public void addUser(MemberEntity memberEntityRecord)
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

  public void updateUser(UpdatingUser user)
  {
    MemberEntity memberEntity = getById(user.getId());

    if (!user.getEmail().isEmpty() && !user.getEmail()
        .equals(memberEntity.getEmail()))
    {
      memberEntity.setEmail(user.getEmail());
    }

    if (!user.getUserName().isEmpty() && !user.getUserName()
        .equals(memberEntity.getUserName()))
    {
      memberEntity.setUserName(user.getUserName());
    }
    if (!user.getPassword().isEmpty() && !user.getPassword()
        .equals(memberEntity.getPassword()))
    {
      memberEntity.setPassword(user.getPassword());
    }

    memberRepository.save(memberEntity);
  }

  public void deleteUser(int id)
  {
    getById(id);
    memberRepository.deleteById(id);
  }

  public MemberEntity getById(int id)
  {
    MemberEntity memberEntity = memberRepository.findById(id);

    if (memberEntity == null)
    {
      throw new IllegalArgumentException("User not found");
    }

    return memberEntity;
  }

  public MemberEntity getByUserName(String username)
  {
    MemberEntity memberEntity = memberRepository.findByUsername(username);

    if(memberEntity == null) {
      throw new IllegalArgumentException("User not found");
    }

    return memberRepository.findByUsername(username);
  }

  public MemberEntity getByEmail(String email)
  {
    MemberEntity memberEntity = memberRepository.findByEmail(email);

    if(memberEntity == null) {
      throw new IllegalArgumentException("User not found");
    }

    return memberRepository.findByEmail(email);
  }

  public Iterable<MemberEntity> getAll()
  {
    return memberRepository.findAll();
  }
}
