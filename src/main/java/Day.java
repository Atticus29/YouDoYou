import java.util.*;
import org.sql2o.*;

public class Day {
  public int id;
  public int tasksCompleted;

  public Day() {
    this.tasksCompleted = Day.getCompleted().size();
  }

  public static List<Task> getCompleted() {
    String sql = "SELECT * FROM tasks WHERE completed = true;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .executeAndFetch(Task.class);
    }
  }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO days (tasksCompleted) VALUES (:tasksCompleted)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("tasksCompleted", this.tasksCompleted)
      .executeUpdate()
      .getKey();
    }
  }
}
