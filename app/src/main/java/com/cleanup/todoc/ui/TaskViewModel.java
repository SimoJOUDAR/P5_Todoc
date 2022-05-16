package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    ProjectRepository mProjectRepository;
    TaskRepository mTaskRepository;
    Executor mExecutor;

    public TaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    public void addTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.addTask(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.deleteTask(task));
    }

    public void deleteAllTasks() {
        mExecutor.execute(() -> mTaskRepository.deleteAllTasks());
    }

    public LiveData<List<Project>> getProjects() {
        return mProjectRepository.getProjects();
    }
}
