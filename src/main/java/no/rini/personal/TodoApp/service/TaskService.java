package no.rini.personal.TodoApp.service;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    ArrayList<Task> tasks = new ArrayList<>();

    public List<Task> getallTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Integer id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    public String addTask(Task task) {
        taskRepository.saveAndFlush(task);
        return "Task Added";
    }

    public String editTask(Task editTask, Integer id) {

        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            taskRepository.saveAndFlush(editTask);
            return "Task edited";
        }
        else
            return "Task does not exist";
    }

    public String deleteTask(Integer id) {
        taskRepository.deleteById(id);
        return "Task deleted";
    }

}
