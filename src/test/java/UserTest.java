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

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

 @Test
 public void user_instantiatesCorrectly_true() {
   User testUser = new User("Jemina");
   testUser.saveUserToDatabase();
   assertTrue(testUser.getUserName().equals("Joe"));

 }


}
