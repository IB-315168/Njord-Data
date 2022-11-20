package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.project.UpdatingProject;
import com.sep3yg9.njorddata.models.ProjectEntity;

public interface ProjectService
{
    void addProject(ProjectEntity projectEntityRecord);
    void updateProject(UpdatingProject project);
    void removeProject(int id);
    ProjectEntity getById(int id);
}
