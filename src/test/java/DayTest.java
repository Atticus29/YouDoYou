// import org.junit.*;
// import static org.junit.Assert.*;
// import org.sql2o.*;
// import java.sql.Timestamp;
// import java.util.Date;
// import java.text.DateFormat;
// import java.time.LocalDateTime;
//
// public class DayTest {
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//   @Test
//   public void testDay_instanceOfDay() {
//     Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
//     Task testTask = new Task("Laundry", newDate, 1, 1, 1,1,1,1,1);
//     testTask.save();
//     Day testDay = new Day();
//     assertEquals(true, testDay instanceof Day);
//   }
//
//   @Test
//   public void getCompleted_returnsValue_1(){
//     Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
//     Task testTask = new Task("Laundry", newDate, 1, 1, 1,1,1,1,1);
//     testTask.save();
//     testTask.update("Vacuum", newDate, 2, 2, 2, 2, true, 1000, 2);
//     Day testDay = new Day();
//     assertEquals(1,testDay.tasksCompleted);
//   }
// }
