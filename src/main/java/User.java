import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User {
  private String name;
  private int id;
  private int level;
  private int experience;
  // private Map<int, int> experienceLevelMap = new HashMap<int,int>();


  public User(String name) {
    this.name = name;
    this.level = 0;
    this.experience = 0;
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

  public int checkIfLevelUp() {
    int calculatedLevel = 0;
    int calcLevelHund = calculatedLevel * 100;

    for (int i = this.experience; i >= 0; i -= calcLevelHund) {
      calculatedLevel++;
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
   }
 }

//this should only be used for manually updating user level? or maybe only be called from within the level check method
 public void updateUserLevel(int level) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE users SET level = :level WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .addParameter("level", this.level)
     .executeUpdate();
   }
 }

  public void saveUserToDatabase() {
     try(Connection con = DB.sql2o.open()) {
       //TODO: add created time to users table
       String sql = "INSERT INTO users (name, level, experience) VALUES (:name, :level, :experience)";
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
       .executeAndFetchFirst(User.class);
       return client;
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
