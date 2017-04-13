import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/tasks.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("template", "templates/task.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasklists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/tasklists.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasklists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      TaskList tasklist = TaskList.find(Integer.parseInt(request.params("id")));
      model.put("tasklist", tasklist);
      model.put("template", "templates/task.vtl");
      model.put("tasks", Task.all());
      model.put("tasklists", TaskList.all());
      model.put("skills", Skill.getAllSkills());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}
