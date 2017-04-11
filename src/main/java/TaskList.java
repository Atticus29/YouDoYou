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
    this.number_tasks = 0;
    // number_tasks will be incremented upon association of task with tasklist (i.e., the task constructor) TODO
  }

  public int getNumber_tasks(){
    return this.number_tasks;
  }

  public void setNumber_tasks(int number){
    this.number_tasks = number;
  }

  public static TaskList find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM task_lists WHERE id=:id ;";
      TaskList TaskList = con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", id)
      .executeAndFetchFirst(TaskList.class);
      return TaskList;
    }
  }

  public boolean equals(TaskList otherTaskList) {
    if(!(otherTaskList instanceof TaskList)) {
      return false;
    } else {
      TaskList newTaskList = (TaskList) otherTaskList;
      return this.getId() == newTaskList.getId();
      // return this.getName().equals(newTaskList.getName()) &&
      // this.getTaskList_list_id() == newTaskList.getTaskList_list_id() &&
      // this.getImportance() == newTaskList.getImportance() &&
      // this.getDue().equals(newTaskList.getDue());
      //TODO add to this
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO task_lists (name, priority_level, number_tasks, created, due, completed, skill_id, user_id) VALUES (:name, :priority_level, :number_tasks, now(), :due, :completed, :skill_id, :user_id);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("priority_level", this.priority_level)
      .addParameter("number_tasks", this.number_tasks)
      .addParameter("due", this.due)
      .addParameter("completed", this.completed)
      .addParameter("skill_id", this.skill_id)
      .addParameter("user_id", this.user_id)
      // .throwOnMappingFailure(false)
      .executeUpdate()
      .getKey();
      String sqlQuery = "SELECT created FROM task_lists WHERE id=:id;";
      this.created = (Timestamp) con.createQuery(sqlQuery)
      .addParameter("id", this.id)
      .executeAndFetchFirst(Timestamp.class);
    }
  }

  public static List<TaskList> all(){
    String sqlQuery = "SELECT * FROM task_lists;";
    try(Connection con=DB.sql2o.open()){
      List<TaskList> results = con.createQuery(sqlQuery)
      .executeAndFetch(TaskList.class);
      return results;
    }
  }

  public void update(String name, Timestamp due, int skill_id, int priority_level, boolean completed) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE task_lists SET name=:name, due=:due, skill_id=:skill_id, priority_level=:priority_level, completed=:completed WHERE id=:id;";
     con.createQuery(sql)
       .addParameter("id", this.id)
       .addParameter("name", name)
       .addParameter("due", due)
       .addParameter("skill_id", skill_id)
       .addParameter("priority_level", priority_level)
       .addParameter("completed", completed)
       .executeUpdate();
   }
 }



}
