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
  private Timestamp newDate;
  private Task testTask;
  private User testUser;
  private Skill testSkill;

  @Before
  public void setUp(){
    testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    testSkill = new Skill("Chore ninja", testUser.getUserId());
    testTaskList = new TaskList("Household Chores", 1, newDate, testSkill.getSkillId(), testUser.getUserId());
    testTaskList.save();
    testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,1,1);
    testTask.associateTaskWithTaskList(testTaskList.getId());
    testTask.associateTaskWithSkill(testSkill.getSkillId());
    testTask.save();
    //TODO could DRY up some tests by adding testTask and linking it to testTaskList (instead of testTaskList2)
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void TaskList_instantiatesCorrectly_true(){
    assertTrue(testTaskList instanceof TaskList);
  }

  @Test
  public void setNumber_tasks_changesNumberTasksTo5_true(){
    assertEquals(0, testTaskList.getNumber_tasks());
    testTaskList.setNumber_tasks(5);
    assertEquals(5, testTaskList.getNumber_tasks());
  }

  @Test
  public void allTasksDone_shouldBeTrueWhenTasksCompletedAndNotTrueWhenANewTaskIsAssociated_true(){
    assertFalse(testTaskList.allTasksDone());
    testTask.markCompleted();
    assertTrue(testTaskList.allTasksDone());
    Task testTask2 = new Task("Make bed", newDate, testUser.getUserId(), 1, 1,1,1);
    testTask2.associateTaskWithTaskList(testTaskList.getId());
    testTask2.save();
    List<Task> allTasks = testTaskList.getTasks();
    for(Task task : allTasks){
      System.out.println("task is completed?: " + task.getCompleted());
    }
    assertFalse(testTaskList.allTasksDone());
    //TODO associate another task and assertFalse again

  }

  @Test
  public void markCompleted_marksATaskListTrueForCompleted_true(){
    assertFalse(testTaskList.getCompleted());
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    testTaskList.markCompleted();
    assertTrue(testTaskList.getCompleted());
  }

  @Test
  public void getters_returnExpectedValues_true(){
    assertEquals("Household Chores", testTaskList.getName());
    Timestamp expectedDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    assertEquals(expectedDate.getDate(), testTaskList.getDue().getDate());
    // assertEquals(1, testTaskList.getUser_id());
    // assertEquals(1, testTaskList.getSkill_id());
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
  public void update_changesNameDueSkillPriorityTaskListListImportanceEstTimeDifficultyNumberTasks_true(){
    Timestamp changeDate = Timestamp.valueOf(LocalDateTime.now().plusDays(20));
    assertFalse(TaskList.find(testTaskList.getId()).getCompleted());
    testTaskList.update("Kitchen chores", changeDate, 2, 2, true, 4);
    assertEquals("Kitchen chores",TaskList.find(testTaskList.getId()).getName());
    assertEquals(changeDate,TaskList.find(testTaskList.getId()).getDue());
    assertEquals(2,TaskList.find(testTaskList.getId()).getSkill_id());
    assertEquals(2,TaskList.find(testTaskList.getId()).getPriority_level());
    assertTrue(TaskList.find(testTaskList.getId()).getCompleted());
    assertEquals(4,TaskList.find(testTaskList.getId()).getNumber_tasks());
  }

  @Test
  public void getTasks_returnsListOfTasks_true(){
    Task testTask2 = new Task("Laundry", newDate, 1, 1, 1,1,1);
    testTask2.associateTaskWithTaskList(testTaskList.getId());
    testTask2.associateTaskWithSkill(testSkill.getSkillId());
    testTask2.save();
    List<Task> allTasks = testTaskList.getTasks();
    assertTrue(allTasks.size() == 2);
    assertTrue(allTasks instanceof List);
  }

  @Test
  public void allTasksDone_correctlyDeterminesWhetherAllTasksHaveBeenCompleted_true(){
    assertFalse(testTaskList.allTasksDone());
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    assertTrue(testTaskList.allTasksDone());
  }

  @Test
  public void markCompleted_onlyWorksIfChildTasksExistAndAreDone_true(){
    // No tasks
    TaskList testTaskList2 = new TaskList("Household Chores", 1, newDate, 1, testUser.getUserId());
    testTaskList2.markCompleted();
    testTaskList2.update(testTaskList2.getName(), testTaskList2.getDue(), testTaskList2.getSkill_id(), testTaskList2.getPriority_level(), testTaskList2.getCompleted(), testTaskList2.getNumber_tasks());
    testTaskList2.save();
    assertFalse(testTaskList2.getCompleted());

    // Add a task but incomplete
    testTask.associateTaskWithTaskList(testTaskList2.getId());
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());

    testTaskList2.markCompleted();
    testTaskList2.update(testTaskList2.getName(), testTaskList2.getDue(), testTaskList2.getSkill_id(), testTaskList2.getPriority_level(), testTaskList2.getCompleted(), testTaskList2.getNumber_tasks());
    assertFalse(testTaskList2.getCompleted());

    // Now complete task
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    testTaskList2.markCompleted();
    testTaskList2.update(testTaskList2.getName(), testTaskList2.getDue(), testTaskList2.getSkill_id(), testTaskList2.getPriority_level(), testTaskList2.getCompleted(), testTaskList2.getNumber_tasks());
    assertTrue(testTaskList2.getCompleted());
  }

  @Test
  public void markCompleted_AddsBonusPointsToUser_true(){
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());

    Task testTask2 = new Task("Make bed", newDate, testUser.getUserId(), 1, 1,1,1);
    testTask2.associateTaskWithTaskList(testTaskList.getId());
    testTask2.associateTaskWithSkill(testSkill.getSkillId());
    testTask2.markCompleted();
    testTask2.save();

    Task testTask3 = new Task("Clean windows", newDate, testUser.getUserId(), 1, 1,1,1);
    testTask3.associateTaskWithTaskList(testTaskList.getId());
    testTask3.associateTaskWithSkill(testSkill.getSkillId());
    testTask3.markCompleted();
    testTask.save();

    assertEquals(3, testTaskList.getTasks().size());

    int oldUserExperience = User.findUser(testUser.getUserId()).getUserExperience();
    System.out.println("oldUserExperience is " + oldUserExperience);
    assertFalse(testTaskList.getCompleted());
    assertFalse(testTaskList.getBonusPointsAdded());

    testTaskList.markCompleted();
    assertTrue(testTaskList.getCompleted());
    assertTrue(testTaskList.getBonusPointsAdded());

    testTaskList.update(testTaskList.getName(), testTaskList.getDue(), testTaskList.getSkill_id(), testTaskList.getPriority_level(), testTaskList.getCompleted(), testTaskList.getNumber_tasks());
    int newUserExperience = User.findUser(testUser.getUserId()).getUserExperience();
    System.out.println("newUserExperience is " + newUserExperience);
    assertTrue(newUserExperience > oldUserExperience);
  }

  @Test
  public void markCompleted_onlyAddBonusPointsOnce_true(){
    // add and complete first and only task. Mark taskList complete
    testTask.markCompleted();
    testTask.update(testTask.getName(), testTask.getDue(), testTask.getSkill_id(), testTask.getPriority_level(), testTask.getTask_list_id(), testTask.getImportance(), testTask.getCompleted(), testTask.getEstimated_time(), testTask.getDifficulty());
    testTaskList.markCompleted();
    System.out.println("Are all tasks done?: " + testTaskList.allTasksDone());
    System.out.println("Old number of tasks is: " + testTaskList.getTasks().size());
    testTaskList.update(testTaskList.getName(), testTaskList.getDue(), testTaskList.getSkill_id(), testTaskList.getPriority_level(), testTaskList.getCompleted(), testTaskList.getNumber_tasks());
    int oldUserExperience = User.findUser(testUser.getUserId()).getUserExperience();
    System.out.println("Old user experience is: " + oldUserExperience);

    // Now add a second task and mark complete. Now try to game the system by getting extra bonus points by marking TaskList complete again
    Task testTask2 = new Task("Dishes", newDate, testUser.getUserId(), 1,1,1,1);
    testTask2.associateTaskWithTaskList(testTaskList.getId());
    testTask2.associateTaskWithSkill(testSkill.getSkillId());
    testTask2.update(testTask2.getName(), testTask2.getDue(), testTask2.getSkill_id(), testTask2.getPriority_level(), testTask2.getTask_list_id(), testTask2.getImportance(), testTask2.getCompleted(), testTask2.getEstimated_time(), testTask2.getDifficulty());
    testTask2.markCompleted();
    testTask2.update(testTask2.getName(), testTask2.getDue(), testTask2.getSkill_id(), testTask2.getPriority_level(), testTask2.getTask_list_id(), testTask2.getImportance(), testTask2.getCompleted(), testTask2.getEstimated_time(), testTask2.getDifficulty());
    TaskList updatedTestTaskList = TaskList.find(testTaskList.getId());
    List<Task> tmp = updatedTestTaskList.getTasks();
    System.out.println("size after new task association is: " + tmp.size());
    System.out.println("Is it done now?: " + updatedTestTaskList.allTasksDone());
    updatedTestTaskList.markCompleted();
    updatedTestTaskList.update(updatedTestTaskList.getName(), updatedTestTaskList.getDue(), updatedTestTaskList.getSkill_id(), updatedTestTaskList.getPriority_level(), updatedTestTaskList.getCompleted(), updatedTestTaskList.getNumber_tasks());
    int newUserExperience = User.findUser(testUser.getUserId()).getUserExperience();
    System.out.println("New user experience is: " + newUserExperience);
    assertEquals(oldUserExperience, newUserExperience);
  }

}
