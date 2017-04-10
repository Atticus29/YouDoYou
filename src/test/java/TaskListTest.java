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
    // testTaskList.save();
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

  // @Test
  // public void delete_removesEntryFromDatabase_true(){
  //   assertTrue(TaskList.all().size() == 1);
  //   testTaskList.delete("TaskLists");
  //   assertTrue(TaskList.all().size() == 0);
  // }
  //
  // //TODO all test
  //
  // @Test
  // public void save_savesEntryIntoDatabase_true(){
  //   assertEquals(1, TaskList.all().size());
  // }
  //
  // @Test
  // public void equals_returnsWhetherAttributesAreEqual_true(){
  //   assertTrue(testTaskList.getId()>0);
  //   assertEquals("Laundry", testTaskList.getName());
  // }
  //
  // @Test
  // public void update_changesNameDueSkillPriorityTaskListListImportanceEstTimeDifficulty_true(){
  //   Timestamp changeDate = Timestamp.valueOf(LocalDateTime.now().plusDays(20));
  //   testTaskList.update("Vacuum", changeDate, 2, 2, 2, 2, true, 1000, 2);
  //   assertEquals("Vacuum",TaskList.find(testTaskList.getId()).getName());
  //   assertEquals(changeDate,TaskList.find(testTaskList.getId()).getDue());
  //   assertEquals(2,TaskList.find(testTaskList.getId()).getSkill_id());
  //   assertEquals(2,TaskList.find(testTaskList.getId()).getPriority_level());
  //   assertEquals(2,TaskList.find(testTaskList.getId()).getTaskList_list_id());
  //   assertEquals(2,TaskList.find(testTaskList.getId()).getImportance());
  //   assertTrue(TaskList.find(testTaskList.getId()).getCompleted());
  //   assertEquals(1000,TaskList.find(testTaskList.getId()).getEstimated_time());
  //   assertEquals(2,TaskList.find(testTaskList.getId()).getDifficulty());
  // }

}
