import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TaskListTest {
  private TaskList testTaskList;

  @Before
  public void setUp(){
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    testTaskList = new TaskList("Household Chores", 1, newDate, 1, 1);
    testTaskList.save();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void TaskList_instantiatesCorrectly_true(){
    assertTrue(testTaskList instanceof TaskList);
  }

  @Test
  public void markCompleted_marksATaskListTrueForCompleted_true(){
    assertFalse(testTaskList.getCompleted());
    testTaskList.markCompleted();
    assertTrue(testTaskList.getCompleted());
  }

  @Test
  public void getters_returnExpectedValues_true(){
    assertEquals("Household Chores", testTaskList.getName());
    Timestamp expectedDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    assertEquals(expectedDate.getDate(), testTaskList.getDue().getDate());
    assertEquals(1, testTaskList.getUser_id());
    assertEquals(1, testTaskList.getSkill_id());
    assertEquals(1, testTaskList.getPriority_level());
    assertFalse(testTaskList.getCompleted());
    testTaskList.setNumber_tasks(2);
    assertEquals(2, testTaskList.getNumber_tasks());
  }

  @Test
  public void delete_removesEntryFromDatabase_true(){
    assertTrue(TaskList.all().size() == 1);
    testTaskList.delete("task_lists");
    assertTrue(TaskList.all().size() == 0);
  }

  //TODO all test

  @Test
  public void save_savesEntryIntoDatabase_true(){
    assertEquals(1, TaskList.all().size());
    assertTrue(TaskList.all().get(0).getCreated() instanceof Timestamp);
  }

  @Test
  public void equals_returnsWhetherAttributesAreEqual_true(){
    assertTrue(testTaskList.getId()>0);
    assertEquals("Household Chores", testTaskList.getName());
    //TODO add to this
  }

  @Test
  public void setNumber_tasks_changesTasks_true(){
    testTaskList.setNumber_tasks(3);
    testTaskList.save();
    assertEquals(3, TaskList.find(testTaskList.getId()).getNumber_tasks());
  }

  @Test
  public void update_changesNameDueSkillPriorityTaskListListImportanceEstTimeDifficulty_true(){
    Timestamp changeDate = Timestamp.valueOf(LocalDateTime.now().plusDays(20));
    assertFalse(TaskList.find(testTaskList.getId()).getCompleted());
    testTaskList.update("Kitchen chores", changeDate, 2, 2, true);
    assertEquals("Kitchen chores",TaskList.find(testTaskList.getId()).getName());
    assertEquals(changeDate,TaskList.find(testTaskList.getId()).getDue());
    assertEquals(2,TaskList.find(testTaskList.getId()).getSkill_id());
    assertEquals(2,TaskList.find(testTaskList.getId()).getPriority_level());
    assertTrue(TaskList.find(testTaskList.getId()).getCompleted());
  }

  @Test
  public void getTasks_returnsListOfTasks_true(){
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    Task testTask1 = new Task("Laundry", newDate, 1, 1, 1,testTaskList.getId(),1,1,1);
    testTask1.save();
    Task testTask2 = new Task("Laundry", newDate, 1, 1, 1,testTaskList.getId(),1,1,1);
    testTask2.save();
    List<Task> allTasks = testTaskList.getTasks();
    assertTrue(allTasks.size() == 2);
    assertTrue(allTasks instanceof List);
  }

  @Test
  public void allTasksDone_correctlyDeterminesWhetherAllTasksHaveBeenCompleted_true(){
    User testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    TaskList testTaskList2 = new TaskList("Household Chores", 1, newDate, 1, testUser.getUserId());
    Task testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,testTaskList2.getId(),1,1,1);
    testTask.save();
    assertFalse(testTaskList2.allTasksDone());
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    assertTrue(testTaskList2.allTasksDone());
  }

  @Test
  public void markCompleted_onlyWorksIfChildTasksExistAndAreDone_true(){
    // No tasks
    testTaskList.markCompleted();
    assertFalse(testTaskList.getCompleted());

    // Add a task but incomplete
    User testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    TaskList testTaskList2 = new TaskList("Household Chores", 1, newDate, 1, testUser.getUserId());
    Task testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,testTaskList2.getId(),1,1,1);
    testTask.save();
    testTaskList.markCompleted();
    assertFalse(testTaskList.getCompleted());

    // Now complete task
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    testTaskList.markCompleted();
    assertTrue(testTaskList.getCompleted());
  }

  @Test
  public void markCompleted_AddsBonusPointsToUser_true(){
    User testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    TaskList testTaskList2 = new TaskList("Household Chores", 1, newDate, 1, testUser.getUserId());
    Task testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,testTaskList2.getId(),1,1,1);
    testTask.save();
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    int oldUserExperience = testUser.getUserExperience();
    testTaskList.markCompleted();
    int newUserExperience = testUser.getUserExperience();
    assertTrue(newUserExperience > oldUserExperience);
  }

  @Test
  public void markCompleted_onlyAddBonusPointsOnce_true(){
    // add and comlete first and only task. Mark taskList complete
    User testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    TaskList testTaskList2 = new TaskList("Household Chores", 1, newDate, 1, testUser.getUserId());
    Task testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,testTaskList2.getId(),1,1,1);
    testTask.save();
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    testTaskList.markCompleted();
    int oldUserExperience = testUser.getUserExperience();

    // Now add a second task and mark complete. Now try to game the system by getting extra bonus points by marking TaskList complete again
    Task testTask2 = new Task("Dishes", newDate, testUser.getUserId(), 1, 1,testTaskList2.getId(),1,1,1);
    testTask2.markCompleted();
    testTask2.update(testTask2.getName(), testTask2.getDue(), testTask2.getSkill_id(), testTask2.getPriority_level(), testTask2.getTask_list_id(), testTask2.getImportance(), testTask2.getCompleted(), testTask2.getEstimated_time(), testTask2.getDifficulty());
    testTaskList.markCompleted();
    int newUserExperience = testUser.getUserExperience();
    assertEquals(oldUserExperience, newUserExperience);
  }

}
