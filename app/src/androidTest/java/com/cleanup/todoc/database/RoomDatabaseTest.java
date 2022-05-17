package com.cleanup.todoc.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RoomDatabaseTest {

    private TodocDatabase database;
    private Task testTask;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        database.projectDao().addProjects(Project.getAllProjects());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    private static final Task TASK_1 = new Task(1, 1L, "Test1", new Date().getTime());
    private static final Task TASK_2 = new Task(2, 2L, "Test2", new Date().getTime());
    private static final Task TASK_3 = new Task(3, 3L, "Test3", new Date().getTime());
    private static final Task TASK_4 = new Task(4, 1L, "Test4", new Date().getTime());

    @Test
    public void A_databaseCreatedWithSuccess() throws InterruptedException {
        List<Task> tasks = RoomDatabaseTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void B_addTasksWithSuccess() throws InterruptedException {

        database.taskDao().addTask(TASK_1);
        database.taskDao().addTask(TASK_2);
        database.taskDao().addTask(TASK_3);

        Task task = RoomDatabaseTestUtil.getValue(database.taskDao().getTaskById(1));

        assertEquals(task.getName(), TASK_1.getName());
        assertEquals(task.getId(), 1);
        assertEquals(task.getProject().getId(), 1L);

        List<Task> tasks = RoomDatabaseTestUtil.getValue(database.taskDao().getAllTasks());

        assertEquals(3, tasks.size());
    }


    @Test
    public void C_generateTaskIdWithSuccess() throws InterruptedException {
        testTask = new Task(0, 1L, "TestTask", new Date().getTime());
        assertEquals(testTask.getId(), 0);
        database.taskDao().addTask(testTask);
        int size = RoomDatabaseTestUtil.getValue(database.taskDao().getAllTasks()).size();
        Task taskFromDb = RoomDatabaseTestUtil.getValue(database.taskDao().getAllTasks()).get(size-1);
        assertTrue(taskFromDb.getId() > 0);
    }



    @Test
    public void D_deleteTaskWithSuccess() throws InterruptedException {

        database.taskDao().addTask(TASK_4);
        int numberOfTasksBeforeDelete = RoomDatabaseTestUtil.getValue(database.taskDao().getAllTasks()).size();
        database.taskDao().deleteTask(TASK_4);
        int numberOfTasksAfterDelete = RoomDatabaseTestUtil.getValue(this.database.taskDao().getAllTasks()).size();

        assertEquals(numberOfTasksBeforeDelete-1, numberOfTasksAfterDelete);
    }

}
