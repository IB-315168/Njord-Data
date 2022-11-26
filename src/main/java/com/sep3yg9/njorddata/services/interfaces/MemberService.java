package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.member.MemberGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.UpdatingMember;
import com.sep3yg9.njorddata.models.MemberEntity;

import java.util.List;

public interface MemberService
{
    void addMember(MemberEntity memberEntityRecord);
    void updateMember(UpdatingMember member);
    void deleteMember(int id);
    List<MemberGrpc> getAllMembers();
    MemberEntity getById(int id);
    MemberEntity getByUserName(String username);
    MemberEntity getByEmail(String email);
    Iterable<MemberEntity> getAll();

}
