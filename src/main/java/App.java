import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.text.SimpleDateFormat;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    // Create a dummy user
    User dummy = new User("Jehosephat");
    // dummy.saveUserToDatabase();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      model.put("template", "templates/index.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      model.put("template", "templates/tasks.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      Task task = Task.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("template", "templates/task.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      String name = request.queryParams("name");
      Timestamp dueDate = User.convertStringToTimestamp(request.queryParams("dueDate"));
      int priority = Integer.parseInt(request.queryParams("priority"));
      int estimatedTime = Integer.parseInt(request.queryParams("estimatedTime"));
      int difficulty = Integer.parseInt(request.queryParams("difficulty"));
      int importance = Integer.parseInt(request.queryParams("importance"));
      int taskListId = Integer.parseInt(request.queryParams("taskListId"));
      int skillId = Integer.parseInt(request.queryParams("skillId"));
      Task newTask = new Task(name, dueDate, 1, priority, importance, estimatedTime, difficulty); // TODO: change that 1 to user.all().get(0)
      newTask.save();
      newTask.associateTaskWithSkill(skillId);
      newTask.associateTaskWithTaskList(taskListId);
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasklists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      model.put("template", "templates/tasklists.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasklists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      TaskList tasklist = TaskList.find(Integer.parseInt(request.params("id")));
      model.put("tasklist", tasklist);
      model.put("template", "templates/tasklist.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasklists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      String name = request.queryParams("name");
      Timestamp dueDate = User.convertStringToTimestamp(request.queryParams("dueDate"));
      int priority = Integer.parseInt(request.queryParams("priority"));
      int skillId = Integer.parseInt(request.queryParams("skillId"));
      int assignedTaskId = Integer.parseInt(request.queryParams("assignedTaskId"));
      TaskList newTaskList = new TaskList(name, priority, dueDate, 1); //TODO: change this 1 to User.all().get(0)
      newTaskList.save();
      newTaskList.associateTaskListWithSkill(skillId);
      Task.find(assignedTaskId).associateTaskWithTaskList(newTaskList.getId());
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/skills", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      model.put("template", "templates/skills.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/skills/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      Skill skill = Skill.findSkill(Integer.parseInt(request.params("id")));
      model.put("skill", skill);
      model.put("template", "templates/skill.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/skills/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int assignedTaskId = Integer.parseInt(request.queryParams("assignedTaskId"));
      int assignedTaskId2 = Integer.parseInt(request.queryParams("assignedTaskId2"));
      int assignedTaskId3 = Integer.parseInt(request.queryParams("assignedTaskId3"));
      int assignedTaskListId = Integer.parseInt(request.queryParams("assignedTaskListId"));
      Skill newSkill = new Skill(name, 1); // TODO: change that 1 to user.all().get(0)
      newSkill.saveSkillToDatabase();
      if (assignedTaskId != 0) {Task.find(assignedTaskId).associateTaskWithSkill(newSkill.getSkillId());}
      if (assignedTaskId2 != 0)
      {Task.find(assignedTaskId2).associateTaskWithSkill(newSkill.getSkillId());}
      if (assignedTaskId3 != 0)
      {Task.find(assignedTaskId3).associateTaskWithSkill(newSkill.getSkillId());}
      if (assignedTaskListId != 0)
      {TaskList.find(assignedTaskListId).associateTaskListWithSkill(newSkill.getSkillId());}
      response.redirect(request.headers("Referer"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/player/:playerName", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", dummy);
      String currentUserName = request.params(":playerName");
      User currentUser = User.findUserByName(currentUserName);
      model.put("player", currentUser);
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      model.put("template", "templates/player.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/duetoday", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.allTasksDueToday());
      model.put("tasklists", Task.allTaskListsDueToday());
      model.put("template", "templates/duetoday.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
