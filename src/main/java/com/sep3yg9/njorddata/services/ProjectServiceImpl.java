package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import com.sep3yg9.njorddata.grpc.protobuf.project.UpdatingProject;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.models.RequirementEntity;
import com.sep3yg9.njorddata.models.SpecificTimeConverter;
import com.sep3yg9.njorddata.repos.ProjectRepository;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository)
    {
        this.projectRepository = projectRepository;
    }

    @Override
    public void addProject(ProjectEntity projectEntityRecord) {
        projectRepository.save(projectEntityRecord);
    }

    @Override
    public void updateProject(UpdatingProject project) {
        ProjectEntity projectEntity = projectRepository.findByIdproject(project.getId());

        if (projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        } else {
            if (!project.getName().isEmpty() && !projectEntity.getName().equals(project.getName())) {
                projectEntity.setName(project.getName());
            }

            if (!projectEntity.getDeadline().equals(SpecificTimeConverter.convertToLocalDateTime(project.getDeadline()))) {
                projectEntity.setDeadline(SpecificTimeConverter.convertToLocalDateTime(project.getDeadline()));
            }

            projectEntity.setRequirements(new LinkedHashSet<>());
            projectRepository.save(projectEntity);

//            Set<RequirementEntity> requirementSet = new LinkedHashSet<>();
            for(Requirement requirement : project.getRequirementsList()) {
                projectEntity.addRequirement(new RequirementEntity(requirement.getId(), projectEntity, requirement.getContent()));
            }

            projectRepository.save(projectEntity);
        }
    }

    @Override
    public void removeProject(int id) {
        getById(id);

        projectRepository.deleteById(id);
    }

    @Override
    public ProjectEntity getById(int id) {
        ProjectEntity projectEntity = projectRepository.findByIdproject(id);

        if(projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        }

        return projectRepository.findByIdproject(id);
    }
}
