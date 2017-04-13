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


  public Task(String name, Timestamp due, int user_id, int priority_level, int importance, int estimated_time, int difficulty) {
    this.name = name;
    this.created = new Timestamp(new Date().getTime()); //TODO
    this.due = due;
    this.user_id = user_id;
    this.priority_level = priority_level;
    this.completed = false;
    this.importance = importance;
    this.estimated_time = estimated_time;
    this.difficulty = difficulty;
  }

  public void associateTaskWithSkill(int skill_id){
    this.skill_id = skill_id;
    this.updateSilently();
  }

  // Note: will also add 1 to associatedTaskList's number_tasks
  public void associateTaskWithTaskList(int task_list_id){
    this.task_list_id = task_list_id;
    this.updateSilently();
    TaskList currentTaskList = TaskList.find(task_list_id);
    currentTaskList.setNumber_tasks(currentTaskList.getNumber_tasks() + 1);
    if(!this.getCompleted()){
      System.out.println("task " + this.name + " is not complete");
      currentTaskList.markIncomplete();
    }
    currentTaskList.updateSilently();
    // (currentTaskList.getName(), currentTaskList.getDue(), currentTaskList.getSkill_id(), currentTaskList.getPriority_level(), currentTaskList.getCompleted(), currentTaskList.getNumber_tasks());

  }

  public void markCompleted(){
    // Update property
    this.completed = true;
    this.update(this.name, this.due, this.skill_id, this.priority_level, this.task_list_id, this.importance, this.completed, this.estimated_time, this.difficulty);

    int pointsToAdd = (int)(10 * Task.POINT_RANGE[this.importance-1]* Task.POINT_RANGE[this.difficulty-1]* calculateEstimatedTimeMultiplier(this.estimated_time));

    User currentUser = User.findUser(this.user_id);
    System.out.println("should be true: " + (currentUser instanceof User));
    int oldExp = User.findUser(currentUser.getUserId()).getUserExperience();
    currentUser.updateUserExperience(oldExp + pointsToAdd);
  }


  public double calculateEstimatedTimeMultiplier(double minutes){
    if(minutes <1){
      throw new UnsupportedOperationException("Inappropriate number of minutes (less than 1)");
    }
    if(minutes == 1){
      return Task.POINT_RANGE[0];
    } else if(minutes > Task.EST_TIME_CEILING){
      return Task.POINT_RANGE[Task.POINT_RANGE.length-1];
    }
    else{
      double incrementor = (Task.EST_TIME_CEILING - 1.0)/9.0;
      int elementIndexToGet = (int) Math.floor(minutes/incrementor);
      return Task.POINT_RANGE[elementIndexToGet];
    }
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

  public void updateSilently(){
    this.update(this.name, this.due, this.skill_id, this.priority_level, this.task_list_id, this.importance, this.completed, this.estimated_time, this.difficulty);
  }

}
