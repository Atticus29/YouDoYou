import org.sql2o.*;

public class DB {

  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://postgresql.cnkomjq87c0e.us-west-2.rds.amazonaws.com:5432/you_do_you", "grimmello", System.getenv("DB_PASS"));
}
