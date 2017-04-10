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




  public void markCompleted(){
    this.completed = true;
  }

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

  // public List<Object> all(String tableName){
  //   String sqlQuery = "SELECT * FROM :table_name WHERE id=:id;";
  //   try(Connection con=DB.sql2o.open()){
  //     List<Object> results = con.createQuery(sqlQuery)
  //     .addParameter("table_name", tableName)
  //     .addParameter("id", this.id)
  //     .executeAndFetch(Object.class);
  //     return results;
  //   }
  // }





}