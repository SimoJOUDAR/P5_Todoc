package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public void addTask(Task task) {
        mTaskDao.addTask(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }

    public LiveData<Task> getTaskById(long id) {
        return mTaskDao.getTaskById(id);
    }

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    public void deleteAllTasks() {
        mTaskDao.deleteAllTasks();
    }
}
