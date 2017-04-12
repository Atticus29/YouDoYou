import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new
    Sql2o("jdbc:postgresql://postgresql.cnkomjq87c0e.us-west-2.rds.amazonaws.com:5432/you_do_you_test", "grimmello", System.getenv("DB_PASS"));
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteAvatarQuery = "DELETE FROM avatars *;";
      con.createQuery(deleteAvatarQuery).executeUpdate();
      String deleteUserQuery = "DELETE FROM users *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      String deleteSkillQuery = "DELETE FROM skills *;";
      con.createQuery(deleteSkillQuery).executeUpdate();
      String deleteTaskListQuery = "DELETE FROM task_lists *;";
      con.createQuery(deleteTaskListQuery).executeUpdate();
      String deleteTaskQuery = "DELETE FROM tasks *;";
      con.createQuery(deleteTaskQuery).executeUpdate();
      String deleteDayQuery = "DELETE FROM days *;";
      con.createQuery(deleteDayQuery).executeUpdate();
    }
  }
}
