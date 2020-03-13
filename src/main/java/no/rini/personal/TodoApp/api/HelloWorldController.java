package no.rini.personal.TodoApp.api;

import no.rini.personal.TodoApp.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class HelloWorldController {


    ArrayList<Task> tasks = new ArrayList<>();

    @GetMapping(value = "/sayHello/{name}", produces = "application/json")
    public ResponseEntity<String> sayHello(@PathVariable("name") String name) {
        return ResponseEntity.ok("Hello " + name);
    }

    @GetMapping(value = "/getTasks", produces = "application/json")
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(value = "/addTask", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        tasks.add(task);
        return ResponseEntity.ok("Task added");
    }

    @PutMapping("/editTask/{id}")
    public ResponseEntity<?> editTask(@Valid @RequestBody Task editTask, @PathVariable("id") Integer id) {
        tasks.removeIf((Task task) -> editTask.getId().equals(id));
        tasks.add(editTask);
        return ResponseEntity.ok("Task edited");
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
        tasks.removeIf((task) -> (task.getId().equals(id)));
        return ResponseEntity.ok("Task deleted");
    }

}
