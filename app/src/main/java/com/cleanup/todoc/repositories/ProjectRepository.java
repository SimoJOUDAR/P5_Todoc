package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectRepository {
    ProjectDao mProjectDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getAllProjects();
    }

    public void getProject(long projectId, ProjectCallback callback) {
        executor.execute(() -> {
            callback.onLoaded(mProjectDao.getProjectById(projectId));
        });
    }

    public interface ProjectCallback {
        void onLoaded(Project project);
    }
}
