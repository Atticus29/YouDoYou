import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class User {
  private String name;
  private int id;
  private int level;
  private int experience;
  private Timestamp created;

  public User(String name) {
    this.name = name;
    this.level = 0;
    this.experience = 0;
  }

  public static Timestamp convertStringToTimestamp(String string) {
    try{
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
      Date parsedDate = dateFormat.parse(string);
      Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
      return timestamp;
    }catch(Exception e){
      return null;
    }
  }

  public String getUserName() {
    return this.name;
  }

  public int getUserId() {
    return this.id;
  }

  public int getUserLevel() {
    return this.level;
  }

  public int getUserExperience() {
    return this.experience;
  }

  public Timestamp getUserCreated() {
    return created;
  }

  public int checkIfLevelUp() {
    int calculatedLevel = 0;
    int calcLevelHund = 100;

    for (int i = this.experience; i >= 0; i -= calcLevelHund) {
      calculatedLevel++;

      if (calculatedLevel == 1) {
        calcLevelHund = 200;
      } else {
        calcLevelHund = calculatedLevel * 100;
      }
    }
    return calculatedLevel;
  }

  //DB stuff below

  public void updateUserName(String newUserName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", newUserName)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  //update experience by taking in new experience amount and adding it to current experience. experience should be cumulative and never reset. IE: level 1 is 100 xp, level 2 is 200 xp, so total xp at level 2 would be 300
  public void updateUserExperience(int experienceAmount) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET experience = :experience WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("experience", this.experience + experienceAmount)
      .executeUpdate();
      this.experience += experienceAmount;
      int newLevel = this.checkIfLevelUp();
      this.updateUserLevel(newLevel);
    }
  }

  //this should only be used for manually updating user level? or maybe only be called from within the level check method
  public void updateUserLevel(int level) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET level = :level WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .addParameter("level", level)
      .executeUpdate();
    }
  }

  public void saveUserToDatabase() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name, level, experience, created) VALUES (:name, :level, :experience, now())";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("level", this.level)
      .addParameter("experience", this.experience)
      .executeUpdate()
      .getKey();
    }
  }

  public static User findUser(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      User client = con.createQuery(sql)
      .addParameter("id", id)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(User.class);
      return client;
    }
  }

  public List<Skill> findAllUserSkills() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM skills WHERE user_id = :id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Skill.class);
    }
  }


  @Override
  public boolean equals(Object otherUser){
    if (!(otherUser instanceof User)){
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getUserName().equals(newUser.getUserName()) && this.getUserId() == newUser.getUserId();
    }
  }


}
