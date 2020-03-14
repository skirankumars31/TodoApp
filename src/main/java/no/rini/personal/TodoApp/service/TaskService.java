package no.rini.personal.TodoApp.service;

import no.rini.personal.TodoApp.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Service
public class TaskService {

    ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getallTasks() {
        return tasks;
    }

    public Task getTask(Integer id) {

        Task foundTask = tasks.stream()
            .filter((Task task) -> task.getId().equals(1))
            .findAny()
            .orElse(null);

        return foundTask;
    }

    public String addTask(Task task) {
        tasks.add(task);
        return "Task Added";
    }

    public String editTask(Task editTask, Integer id) {
        tasks.removeIf((Task task) -> editTask.getId().equals(id));
        tasks.add(editTask);
        return "Task edited";
    }

    public String deleteTask(Integer id) {
        tasks.removeIf((task) -> (task.getId().equals(id)));
        return "Task deleted";
    }

}
