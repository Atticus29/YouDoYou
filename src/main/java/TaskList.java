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
  private boolean bonusPointsAdded;

  public TaskList(String name, int priority_level, Timestamp due, int skill_id, int user_id) {
    this.name = name;

    if(priority_level >= MIN_ALL && priority_level <= MAX_PRIORITY){
      this.priority_level = priority_level;
    } else{
      throw new UnsupportedOperationException("Priority out of range");
    }

    this.due = due;
    this.completed = false;
    this.skill_id = skill_id;
    this.user_id = user_id;
    this.number_tasks = 0;
    this.bonusPointsAdded = false;
    // number_tasks will be incremented upon association of task with tasklist (i.e., the task constructor) TODO
  }

  public boolean getBonusPointsAdded(){
    return this.bonusPointsAdded;
  }

  public boolean allTasksDone(){
    List<Task> tasksInThisTaskList = this.getTasks();
    boolean returnVal = true;
    for(Task task : tasksInThisTaskList){
      if (task.getCompleted() == false){
        returnVal = false;
      }
    }
    return returnVal;
  }

  public void markIncomplete(){
    this.completed = false;
    this.updateSilently();
  }

  public void markCompleted(){
    if (this.getTasks().size() > 0 && this.allTasksDone()){
      this.completed = true;
      //TODO find out why number_tasks isn't incrementing. For now, a workaround
      if(this.getTasks().size() > 2 && !this.bonusPointsAdded){
        int bonusPoints = 5 * this.getTasks().size();
        System.out.println("bonusPoints is: " + bonusPoints);
        User currentUser = User.findUser(this.getUser_id());
        int oldExp = currentUser.getUserExperience();
        currentUser.updateUserExperience(oldExp + bonusPoints);
        this.bonusPointsAdded = true;
      }
      this.updateSilently();
    }

  }

  public int getNumber_tasks(){
    return this.number_tasks;
  }

  public void setNumber_tasks(int number){
    this.number_tasks = number;
  }

  public static TaskList find(int id) {
    try(Connection con = DB.sql2o.open()){
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

  public void update(String name, Timestamp due, int skill_id, int priority_level, boolean completed, int number_tasks) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE task_lists SET name=:name, due=:due, skill_id=:skill_id, priority_level=:priority_level, completed=:completed, number_tasks=:number_tasks WHERE id=:id;";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("name", name)
      .addParameter("due", due)
      .addParameter("skill_id", skill_id)
      .addParameter("priority_level", priority_level)
      .addParameter("completed", completed)
      .addParameter("number_tasks", number_tasks)
      .executeUpdate();
    }
  }

  public List<Task> getTasks(){
    String sqlQuery = "SELECT * FROM tasks WHERE task_list_id=:task_list_id;";
    try(Connection con=DB.sql2o.open()){
      List<Task> results = con.createQuery(sqlQuery)
      .addParameter("task_list_id", this.id)
      .executeAndFetch(Task.class);
      return results;
    }
  }

  public void updateSilently(){
    this.update(this.name, this.due, this.skill_id, this.priority_level, this.completed, this.number_tasks);
  }

}
