package com.sep3yg9.njorddata.services;

import com.google.type.DateTime;
import com.sep3yg9.njorddata.grpc.protobuf.project.UpdatingProject;
import com.sep3yg9.njorddata.models.ProjectEntity;
import com.sep3yg9.njorddata.repos.ProjectRepository;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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
        Optional<ProjectEntity> projectEntity1 = projectRepository.findById(1);

        ProjectEntity projectEntity = projectEntity1.get();
        if (projectEntity == null) {
            System.out.println("Project does not exist");
        } else {
            if (!project.getName().isEmpty() && !projectEntity.getName().equals(project.getName())) {
                projectEntity.setName(project.getName());
            }
            if (!projectEntity.getDeadline().equals(project.getDeadline())) {
                projectEntity.setDeadline(LocalDateTime.of(LocalDate.of(project.getDeadline().getYear(), project.getDeadline().getMonth(), project.getDeadline().getDay()),
                        LocalTime.of(project.getDeadline().getHour(), project.getDeadline().getMinute())));
            }
            projectRepository.save(projectEntity);
        }
    }

    @Override
    public void removeProject(int id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectEntity getById(int id) {
        return projectRepository.findByIdproject(id);
    }
}
