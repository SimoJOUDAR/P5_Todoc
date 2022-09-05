package com.cleanup.todoc.injections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory viewModelFactory;
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        mProjectRepository = new ProjectRepository(database.projectDao());
        mTaskRepository = new TaskRepository(database.taskDao());
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public static synchronized ViewModelFactory getInstance(Context context) {
        if (viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(context);
        }
        return viewModelFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T viewModel = null;
        try {
            if (modelClass.isAssignableFrom(TaskViewModel.class)) {
                return viewModel = modelClass.getConstructor(ProjectRepository.class, TaskRepository.class, Executor.class).newInstance(mProjectRepository, mTaskRepository, mExecutor);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
