import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class TodoAbstract {
  public String name;
  public Timestamp created;
  public Timestamp due;
  public int user_id;
  public int skill_id;
  public int priority_level;
  public boolean completed;
  public int id;
  public static final double[] POINT_RANGE = {0.2, 0.4, 0.6, 0.8, 1, 1.2, 1.4, 1.6, 1.8, 2};
  public static final int EST_TIME_CEILING = 120;

  public String getName(){
    return this.name;
  }

  public Timestamp getCreated(){
    return this.created;
  }

  public Timestamp getDue(){
    return this.due;
  }

  public int getUser_id(){
    return this.user_id;
  }

  public int getSkill_id(){
    return this.skill_id;
  }

  public int getPriority_level(){
    return this.priority_level;
  }

  public boolean getCompleted(){
    return this.completed;
  }

  public int getId(){
    return this.id;
  }

  public void delete(String tableName){
    String sqlQuery = String.format("DELETE FROM %s WHERE id=:id;", tableName);
    try(Connection con=DB.sql2o.open()){
      con.createQuery(sqlQuery)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
