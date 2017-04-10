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
  private int estimated_time;
  private int importance;
  private int difficulty;

  public Task(String name, Timestamp due, int user_id, int skill_id, int priority_level, int task_list_id, int importance, int estimated_time, int difficulty) {
    this.name = name;
    this.created = new Timestamp(new Date().getTime()); //TODO
    this.due = due;
    this.user_id = user_id;
    this.skill_id = skill_id;
    this.priority_level = priority_level;
    this.completed = false;
    this.task_list_id = task_list_id;
    this.importance = importance;
    this.estimated_time = estimated_time;
    this.difficulty = difficulty;
  }

  public int getImportance(){
    return this.importance;
  }

  public int getEstimated_time(){
    return this.estimated_time;
  }


  public int getDifficulty(){
    return this.difficulty;
  }

  public int getTask_list_id(){
    return this.task_list_id;
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id=:id ;";
      Task Task = con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
      return Task;
    }
  }

  public boolean equals(Task otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getId() == newTask.getId();
      // return this.getName().equals(newTask.getName()) &&
      // this.getTask_list_id() == newTask.getTask_list_id() &&
      // this.getImportance() == newTask.getImportance() &&
      // this.getDue().equals(newTask.getDue());
      //TODO add to this
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (name, created, due, difficulty, estimated_time, importance, completed, priority_level, skill_id, task_list_id, user_id) VALUES (:name, now(), :due, :difficulty, :estimated_time, :importance, :completed, :priority_level, :skill_id, :task_list_id, :user_id);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("due", this.due)
      .addParameter("difficulty", this.difficulty)
      .addParameter("estimated_time", this.estimated_time)
      .addParameter("importance", this.importance)
      .addParameter("completed", this.completed)
      .addParameter("priority_level", this.priority_level)
      .addParameter("skill_id", this.skill_id)
      .addParameter("task_list_id", this.task_list_id)
      .addParameter("user_id", this.user_id)
      .throwOnMappingFailure(false)
      .executeUpdate()
      .getKey();
      String sqlQuery = "SELECT created FROM tasks WHERE id=:id;";
      this.created = (Timestamp) con.createQuery(sqlQuery)
      .addParameter("id", this.id)
      .executeAndFetchFirst(Timestamp.class);
    }
  }

  public static List<Task> all(){
    String sqlQuery = "SELECT * FROM tasks;";
    try(Connection con=DB.sql2o.open()){
      List<Task> results = con.createQuery(sqlQuery)
      .executeAndFetch(Task.class);
      return results;
    }
  }

  public void update(String name, Timestamp due, int skill_id, int priority_level, int task_list_id, int importance, boolean completed, int estimated_time, int difficulty) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE tasks SET name=:name, due=:due, skill_id=:skill_id, priority_level=:priority_level, task_list_id=:task_list_id, importance=:importance, completed=:completed, estimated_time=:estimated_time, difficulty=:difficulty WHERE id=:id;";
     con.createQuery(sql)
       .addParameter("id", this.id)
       .addParameter("name", name)
       .addParameter("due", due)
       .addParameter("skill_id", skill_id)
       .addParameter("priority_level", priority_level)
       .addParameter("task_list_id", task_list_id)
       .addParameter("importance", importance)
       .addParameter("completed", completed)
       .addParameter("estimated_time", estimated_time)
       .addParameter("difficulty", difficulty)
       .executeUpdate();
   }
 }

}
