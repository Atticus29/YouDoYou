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

public class TaskListTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

}

// @Test
// public void update_changesNameBadgeNumberEmail_true(){
//   testRanger.update("Fran", 100, "mark.fisher3@pcc.edu");
//   assertEquals("Fran",Ranger.findRanger(testRanger.getId()).getName());
//   assertEquals(100,Ranger.findRanger(testRanger.getId()).getBadge_number());
//   assertEquals("mark.fisher3@pcc.edu",Ranger.findRanger(testRanger.getId()).getEmail());
// }
