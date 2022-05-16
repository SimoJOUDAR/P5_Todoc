package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM Task")
    void deleteAllTasks();
}
