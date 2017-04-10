import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public abstract class TodoAbstract {
  public String name;
  public Timestamp created;
  public Timestamp due_time;
  public int user_id;
  public int skill_id;
  public int priority_level;
}
