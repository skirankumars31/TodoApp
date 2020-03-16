package no.rini.personal.TodoApp.api;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @Autowired
    TaskService taskService;

    Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping(value = "/sayHello/{name}", produces = "application/json")
    public ResponseEntity<String> sayHello(@PathVariable("name") String name) {
        logger.info("Invoked sayHello Method");
        return ResponseEntity.ok("Hello " + name);
    }

    @GetMapping(value = "/getallTasks", produces = "application/json")
    public ResponseEntity<?> getallTasks() {
        logger.info("Invoked getallTasks Method");
        return ResponseEntity.ok(taskService.getallTasks());
    }

    @GetMapping(value = "/getTask/{id}", produces = "application/json")
    public ResponseEntity<?> getTask(@PathVariable("id") Integer id) {
        logger.info("Invoked getTask Method");
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping(value = "/addTask", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        logger.info("Invoked addTask Method");
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("/editTask/{id}")
    public ResponseEntity<?> editTask(@Valid @RequestBody Task editTask, @PathVariable("id") Integer id) {
        logger.info("Invoked editTask Method");
        taskService.getallTasks().removeIf((Task task) -> editTask.getId().equals(id));
        taskService.addTask(editTask);
        return ResponseEntity.ok(taskService.editTask(editTask, id));
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
        logger.info("Invoked deleteTask Method");
        taskService.getallTasks().removeIf((task) -> (task.getId().equals(id)));
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

}
