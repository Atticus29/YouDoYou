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
// public class SkillTest {
//
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//   User testUser;
//   User testUser2;
//   Skill testSkill; //these are for pre-populating the DB
//   Skill testSkill2;
//
//   @Before
//   public void skillPopulator() {
//     testUser = new User("Jemina");
//     testUser.saveUserToDatabase();
//     testUser2 = new User("Jehosephat");
//     testUser2.saveUserToDatabase();
//     testSkill = new Skill("Java", testUser.getUserId());
//     testSkill.saveSkillToDatabase();
//     testSkill2 = new Skill("Health", testUser2.getUserId());
//     testSkill2.saveSkillToDatabase();
//   }
//
//   @Test
//   public void skill_instantiatesCorrectly_true() {
//     Timestamp rightNow = new Timestamp(new Date().getTime());
//     assertTrue(testSkill.getSkillName().equals("Java"));
//     assertTrue(testSkill.getSkillLevel() == 0);
//     assertTrue(testSkill.getSkillExperience() == 0);
//     assertTrue(testSkill.getSkillId() > 0);
//     assertEquals(rightNow.getDay(), Skill.findSkill(testSkill.getSkillId()).getSkillCreated().getDay());
//   }
//
//   @Test
//   public void find_findsProperSkill_true() {
//     assertEquals(testSkill, Skill.findSkill(testSkill.getSkillId()));
//   }
//
//   @Test
//   public void update_updatesCorrectly_true() {
//     testSkill.updateSkillName("Jerry");
//     assertEquals("Jerry", Skill.findSkill(testSkill.getSkillId()).getSkillName());
//     testSkill.updateSkillLevel(1);
//     assertEquals(1, Skill.findSkill(testSkill.getSkillId()).getSkillLevel());
//     testSkill.updateSkillExperience(1);
//     assertEquals(1, Skill.findSkill(testSkill.getSkillId()).getSkillExperience());
//   }
//
//   @Test
//   public void level_calculatesCorrectly_true() {
//     testSkill.updateSkillExperience(1000);
//     assertEquals(4, Skill.findSkill(testSkill.getSkillId()).checkIfLevelUp());
//   }
//
//   @Test
//   public void get_getsUserIdCorrectly_true() {
//     assertEquals(testUser.getUserId(), testSkill.getSkillUserId());
//   }
//
//
// }
