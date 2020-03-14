package no.rini.personal.TodoApp.api;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @Autowired
    TaskService taskService;

    ArrayList<Task> tasks = new ArrayList<>();

    @GetMapping(value = "/sayHello/{name}", produces = "application/json")
    public ResponseEntity<String> sayHello(@PathVariable("name") String name) {
        return ResponseEntity.ok("Hello " + name);
    }

    @GetMapping(value = "/getallTasks", produces = "application/json")
    public ResponseEntity<?> getallTasks() {
        return ResponseEntity.ok(taskService.getallTasks());
    }

    @GetMapping(value = "/getTask/{id}", produces = "application/json")
    public ResponseEntity<?> getTask(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping(value = "/addTask", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("/editTask/{id}")
    public ResponseEntity<?> editTask(@Valid @RequestBody Task editTask, @PathVariable("id") Integer id) {
        tasks.removeIf((Task task) -> editTask.getId().equals(id));
        tasks.add(editTask);
        return ResponseEntity.ok(taskService.editTask(editTask,id));
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
        tasks.removeIf((task) -> (task.getId().equals(id)));
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

}
