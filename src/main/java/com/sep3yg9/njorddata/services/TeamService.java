package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.repos.TeamRepository;
import com.sep3yg9.njorddata.repos.TeammemberRepository;
import com.sep3yg9.njorddata.repos.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService
{
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeammemberRepository teammemberRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository, TeammemberRepository teammemberRepository)
    {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teammemberRepository = teammemberRepository;
    }

    public void addTeam(TeamEntity teamEntityRecord){
        teamRepository.save(teamEntityRecord);
    }

    public TeamEntity getById(int id){
        return teamRepository.findByIdteam(id);
    }

    public List<TeamEntity> getByTeamLeaderId(int id) {
        return teamRepository.findByTeamleader(id);
    }

    public TeamEntity getByName(String name){
        return teamRepository.findByName(name);
    }
    public void removeTeam(int id){
        teamRepository.deleteById(id);
    }

    public void updateTeam(UpdatingTeam team){
        TeamEntity teamEntity = teamRepository.findByIdteam(team.getId());

        if(teamEntity == null){
            System.out.println("Team does not exist");
        }
        else{
            if(!team.getName().isEmpty() && !team.getName().equals(teamEntity.getName())){
                teamEntity.setName(team.getName());
            }

            ArrayList<UserEntity> newMembers = new ArrayList<>();
            for(User user : team.getMembersList()) {
                newMembers.add(userRepository.findById(user.getId()));
            }

            ArrayList<UserEntity> oldMembers = new ArrayList<>();
            for(TeamMember teamMember : teamEntity.getMembers()) {
                oldMembers.add(userRepository.findById(teamMember.getUserEntity()
                    .getIdmember()));
            }

            teamEntity.setMembers(new ArrayList<TeamMember>());
            teamRepository.save(teamEntity);

            for(UserEntity user : newMembers) {
                teamEntity.addMember(user);
            }

        }

        System.out.println(teamEntity.getMembers().size());
        teamRepository.save(teamEntity);
    }
}
