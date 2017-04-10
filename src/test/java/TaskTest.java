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

public class TaskTest {
  private Task testTask;

  @Before
  public void setUp(){
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    testTask = new Task("Laundry", newDate, 1, 1, 1);
    // testTask.save();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();



}
