import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Task extends TodoAbstract{ //implements DatabaseManagement {
  private int task_list_id;
  private int importance;

  public Task(String name, Timestamp due_time, int user_id, int skill_id, int priority_level, int task_list_id, int importance) {
    this.name = name;
    this.created = new Timestamp(new Date().getTime()); //TODO
    this.due_time = due_time;
    this.user_id = user_id;
    this.skill_id = skill_id;
    this.priority_level = priority_level;
    this.completed = false;
    this.task_list_id = task_list_id;
    this.importance = importance;
  }

  public int getTask_list_id(){
    return this.task_list_id;
  }

  public int getImportance(){
    return this.importance;
  }

}
