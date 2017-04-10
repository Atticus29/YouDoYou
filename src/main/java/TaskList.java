import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TaskList extends TodoAbstract {
  private int number_tasks;

  public TaskList(String name, int priority_level, Timestamp due, int skill_id, int user_id) {
    this.name = name;
    this.priority_level = priority_level;
    this.due = due;
    this.completed = false;
    this.skill_id = skill_id;
    this.user_id = user_id;
    // number_tasks will be incremented upon association of task with tasklist (i.e., the task constructor) TODO
  }

  public int getNumber_tasks(){
    return this.number_tasks;
  }

  public void markCompleted(){
    this.completed = true;
  }

  public void setNumber_tasks(int number){
    this.number_tasks = number;
  }

}
