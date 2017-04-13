public class TaskTest {
  private Task testTask;
  private User testUser;
  private Timestamp newDate;

  @Before
  public void setUp(){
    testUser = new User("Jemina");
    testUser.saveUserToDatabase();
    newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    testTask = new Task("Laundry", newDate, testUser.getUserId(), 1, 1,1,1);
    testTask.save();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void task_instantiatesCorrectly_true(){
    assertTrue(testTask instanceof Task);
  }

  @Test
  public void markCompleted_marksATaskTrueForCompleted_true(){
    assertFalse(testTask.getCompleted());
    testTask.markCompleted();
    assertTrue(testTask.getCompleted());

  }

  @Test
  public void markCompleted_forSuperEasyTaskAddsTenPointsToUsersExperienceLevel_true(){
    assertEquals(0, testUser.getUserExperience());
    testTask.markCompleted();
    assertEquals(10, User.findUser(testUser.getUserId()).getUserExperience());
  }

  @Test
  public void associateTaskWithTaskList_GivesCorrectTaskI_true(){
    TaskList testTaskList = new TaskList("Drudgery", 1, newDate, 7,7);
    testTaskList.save();
    int testTaskListId = testTaskList.getId();
    testTask.associateTaskWithTaskList(testTaskListId);
    assertEquals(testTaskListId, testTask.getTask_list_id());
  }

  @Test
  public void associateTaskWithTaskList_incrementsNumberTasksOfAssociatedTaskList_true(){
    TaskList testTaskList = new TaskList("Drudgery", 1, newDate, 7,7);
    testTaskList.save();
    assertEquals(0, testTaskList.getNumber_tasks());
    int testTaskListId = testTaskList.getId();
    testTask.associateTaskWithTaskList(testTaskListId);
    TaskList currentTaskList = TaskList.find(testTaskListId);
    assertEquals(1, currentTaskList.getNumber_tasks());
  }

  // @Test
  //TODO bring this back in
  // public void getters_returnExpectedValues_true(){
  //   assertEquals("Laundry", testTask.getName());
  //   Timestamp expectedDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
  //   assertEquals(expectedDate.getDate(), testTask.getDue().getDate());
  //   assertEquals(testUser.getUserId(), testTask.getUser_id());
  //   assertEquals(testSkill.getSkillId(), testTask.getSkill_id());
  //   assertEquals(1, testTask.getPriority_level());
  //   assertFalse(testTask.getCompleted());
  //   assertEquals(1, testTask.getTask_list_id());
  //   assertEquals(1, testTask.getImportance());
  //   assertEquals(1, testTask.getEstimated_time());
  //   assertEquals(1, testTask.getDifficulty());
  // }

  @Test
  public void delete_removesEntryFromDatabase_true(){
    assertTrue(Task.all().size() == 1);
    testTask.delete("tasks");
    assertTrue(Task.all().size() == 0);
  }

  //TODO all test

  @Test
  public void save_savesEntryIntoDatabase_true(){
    assertEquals(1, Task.all().size());
  }

  @Test
  public void equals_returnsWhetherAttributesAreEqual_true(){
    assertTrue(testTask.getId()>0);
    assertEquals("Laundry", testTask.getName());
  }

  @Test
  public void update_changesNameDueSkillPriorityTaskListImportanceEstTimeDifficulty_true(){
    Timestamp changeDate = Timestamp.valueOf(LocalDateTime.now().plusDays(20));
    testTask.update("Vacuum", changeDate, 2, 2, 2, 2, true, 1000, 2);
    assertEquals("Vacuum",Task.find(testTask.getId()).getName());
    assertEquals(changeDate,Task.find(testTask.getId()).getDue());
    assertEquals(2,Task.find(testTask.getId()).getSkill_id());
    assertEquals(2,Task.find(testTask.getId()).getPriority_level());
    assertEquals(2,Task.find(testTask.getId()).getTask_list_id());
    assertEquals(2,Task.find(testTask.getId()).getImportance());
    assertTrue(Task.find(testTask.getId()).getCompleted());
    assertEquals(1000,Task.find(testTask.getId()).getEstimated_time());
    assertEquals(2,Task.find(testTask.getId()).getDifficulty());
  }

  @Test
  public void calculateEstimatedTimeMultiplier_returnsDoublesCorrespondingToTheConstArray_true(){
    Timestamp newDate = Timestamp.valueOf(LocalDateTime.now().plusDays(10));
    Task testTask1 = new Task("Laundry", newDate, 1, 1, 1,119,1);
    assertEquals(2.0, testTask1.calculateEstimatedTimeMultiplier(testTask1.getEstimated_time()),0.001);
    testTask1 = new Task("Laundry", newDate, 1, 1, 1,1,1);
    assertEquals(1.0, testTask1.calculateEstimatedTimeMultiplier(testTask1.getEstimated_time()),0.001);
    testTask1 = new Task("Laundry", newDate, 1, 1, 1,120,1);
    assertEquals(2.0, testTask1.calculateEstimatedTimeMultiplier(testTask1.getEstimated_time()),0.001);
    testTask1 = new Task("Laundry", newDate, 1, 1, 1,3000,1);
    assertEquals(2, testTask1.calculateEstimatedTimeMultiplier(testTask1.getEstimated_time()),0.001);
  }

}