// import org.sql2o.*;
// import org.junit.*;
// import static org.junit.Assert.*;
// import java.util.Arrays;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.sql.Timestamp;
//
// public class UserTest {
//
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//   User testUser; //these are for pre-populating the DB
//   User testUser2;
//
//   @Before
//   public void userPopulator() {
//     testUser = new User("Jemina");
//     testUser.saveUserToDatabase();
//     testUser2 = new User("Jehosephat");
//     testUser2.saveUserToDatabase();
//   }
//
//  @Test
//  public void user_instantiatesCorrectly_true() {
//    Timestamp rightNow = new Timestamp(new Date().getTime());
//    assertTrue(testUser.getUserName().equals("Jemina"));
//    assertTrue(testUser.getUserLevel() == 0);
//    assertTrue(testUser.getUserExperience() == 0);
//    assertTrue(testUser.getUserId() > 0);
//    assertEquals(rightNow.getDay(), User.findUser(testUser.getUserId()).getUserCreated().getDay());
//  }
//
//  @Test
//  public void find_findsProperUser_true() {
//    assertEquals(testUser, User.findUser(testUser.getUserId()));
//  }
//
//  @Test
//  public void update_updatesCorrectly_true() {
//    testUser.updateUserName("Jerry");
//    assertEquals("Jerry", User.findUser(testUser.getUserId()).getUserName());
//    testUser.updateUserLevel(1);
//    assertEquals(1, User.findUser(testUser.getUserId()).getUserLevel());
//    testUser.updateUserExperience(1);
//    assertEquals(1, User.findUser(testUser.getUserId()).getUserExperience());
//  }
//
//  @Test
//  public void level_calculatesCorrectly_true() {
//    testUser.updateUserExperience(1000);
//    assertEquals(4, User.findUser(testUser.getUserId()).checkIfLevelUp());
//  }
//
//  @Test
//  public void find_findsAllUserSkills_true() {
//    Skill testSkill = new Skill("Jumping", testUser.getUserId());
//    testSkill.saveSkillToDatabase();
//    assertEquals(testSkill, User.findUser(testUser.getUserId()).findAllUserSkills().get(0));
//  }
//
//
// }
