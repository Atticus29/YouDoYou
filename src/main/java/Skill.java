import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Skill {
  private String name;
  private int id;
  private int userId;
  private int level;
  private int experience;

  public Skill(String name, int userId) {
    this.name = name;
    this.userId = userId;
    this.level = 0;
    this.experience = 0;
  }



}
