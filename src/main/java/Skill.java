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
  private Timestamp created;
  // private int numTasksWithSkillCompleted;
  // TODO: implement numTasksWithSkillCompleted getter/save/etc
  public Skill(String name, int userId) {
    this.name = name;
    this.userId = userId;
    this.level = 0;
    this.experience = 0;
    // this.numTasksWithSkillCompleted = 0;
  }

  public String getSkillName() {
    return this.name;
  }

  public int getSkillId() {
    return this.id;
  }

  public int getSkillLevel() {
    return this.level;
  }

  public int getSkillExperience() {
    return this.experience;
  }

  public int getSkillUserId() {
    return this.userId;
  }

  public Timestamp getSkillCreated() {
    return this.created;
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

  // TODO: add a getAllTasks
  public static List<Skill> getAllSkills() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM skills";
      List<Skill> skills = con.createQuery(sql)
      .addColumnMapping("user_id", "userId")
      .throwOnMappingFailure(false)
      .executeAndFetch(Skill.class);
      return skills;
    }
  }

  public void updateSkillName(String newSkillName) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE skills SET name = :name WHERE id = :id";
     con.createQuery(sql)
     .addParameter("name", newSkillName)
     .addColumnMapping("user_id", "userId")
     .addParameter("id", this.id)
     .executeUpdate();
   }
  }

  //update experience by taking in new experience amount and adding it to current experience. experience should be cumulative and never reset. IE: level 1 is 100 xp, level 2 is 200 xp, so total xp at level 2 would be 300
  public void updateSkillExperience(int experienceAmount) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE skills SET experience = :experience WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .addParameter("experience", this.experience + experienceAmount)
     .addColumnMapping("user_id", "userId")
     .executeUpdate();
   }
  }

  //this should only be used for manually updating user level? or maybe only be called from within the level check method
  public void updateSkillLevel(int level) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE skills SET level = :level WHERE id = :id";
     con.createQuery(sql)
     .addParameter("id", id)
     .addParameter("level", level)
     .addColumnMapping("user_id", "userId")
     .executeUpdate();
   }
  }

  public void saveSkillToDatabase() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO skills (name, level, experience, created, user_id) VALUES (:name, :level, :experience, now(), :userId)";
       this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("level", this.level)
       .addParameter("experience", this.experience)
       .addParameter("userId", this.userId)
       .addColumnMapping("user_id", "userId")
       .executeUpdate()
       .getKey();
     }
   }

   public static Skill findSkill(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM skills WHERE id = :id";
       Skill client = con.createQuery(sql)
       .addParameter("id", id)
       .addColumnMapping("user_id", "userId")
       .throwOnMappingFailure(false)
       .executeAndFetchFirst(Skill.class);
       return client;
     }
   }


   @Override
   public boolean equals(Object otherSkill){
     if (!(otherSkill instanceof Skill)){
       return false;
     } else {
       Skill newSkill = (Skill) otherSkill;
       return this.getSkillName().equals(newSkill.getSkillName()) && this.getSkillId() == newSkill.getSkillId();
     }
   }



}
