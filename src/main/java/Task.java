import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Task implements DatabaseManagement {
  private String name;
  private Timestamp created;
  private Timestamp due_time;
  private int difficulty;

  public Task() {

  }

}
