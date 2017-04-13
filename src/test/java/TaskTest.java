// import org.sql2o.*;
// import org.junit.*;
// import static org.junit.Assert.*;
// import java.util.Arrays;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.sql.Timestamp;
// import java.time.LocalDateTime;
// //
// public class TaskTest {
//   private Task testTask;
//
//   @Before
//   public void setUp(){
//     Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
//     testTask = new Task("Laundry", newDate, 1, 1, 1,1,1,1,1);
//     testTask.save();
//   }
//
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//   @Test
//   public void task_instantiatesCorrectly_true(){
//     assertTrue(testTask instanceof Task);
//   }
//
//   @Test
//   public void markCompleted_marksATaskTrueForCompleted_true(){
//     assertFalse(testTask.getCompleted());
//     testTask.markCompleted();
//     assertTrue(testTask.getCompleted());
//   }
//
//   @Test
//   public void getters_returnExpectedValues_true(){
//     assertEquals("Laundry", testTask.getName());
//     Timestamp expectedDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
//     assertEquals(expectedDate.getDate(), testTask.getDue().getDate());
//     assertEquals(1, testTask.getUser_id());
//     assertEquals(1, testTask.getSkill_id());
//     assertEquals(1, testTask.getPriority_level());
//     assertFalse(testTask.getCompleted());
//     assertEquals(1, testTask.getTask_list_id());
//     assertEquals(1, testTask.getImportance());
//     assertEquals(1, testTask.getEstimated_time());
//     assertEquals(1, testTask.getDifficulty());
//   }
//
//   @Test
//   public void delete_removesEntryFromDatabase_true(){
//     assertTrue(Task.all().size() == 1);
//     testTask.delete("tasks");
//     assertTrue(Task.all().size() == 0);
//   }
//
//   //TODO all test
//
//   @Test
//   public void save_savesEntryIntoDatabase_true(){
//     assertEquals(1, Task.all().size());
//   }
//
//   @Test
//   public void equals_returnsWhetherAttributesAreEqual_true(){
//     assertTrue(testTask.getId()>0);
//     assertEquals("Laundry", testTask.getName());
//   }
//
//   @Test
//   public void update_changesNameDueSkillPriorityTaskListImportanceEstTimeDifficulty_true(){
//     Timestamp changeDate = Timestamp.valueOf(LocalDateTime.now().plusDays(20));
//     testTask.update("Vacuum", changeDate, 2, 2, 2, 2, true, 1000, 2);
//     assertEquals("Vacuum",Task.find(testTask.getId()).getName());
//     assertEquals(changeDate,Task.find(testTask.getId()).getDue());
//     assertEquals(2,Task.find(testTask.getId()).getSkill_id());
//     assertEquals(2,Task.find(testTask.getId()).getPriority_level());
//     assertEquals(2,Task.find(testTask.getId()).getTask_list_id());
//     assertEquals(2,Task.find(testTask.getId()).getImportance());
//     assertTrue(Task.find(testTask.getId()).getCompleted());
//     assertEquals(1000,Task.find(testTask.getId()).getEstimated_time());
//     assertEquals(2,Task.find(testTask.getId()).getDifficulty());
//   }
//
// }
