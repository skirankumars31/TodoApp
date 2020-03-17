package no.rini.personal.TodoApp.service;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(TaskService.class);

    ArrayList<Task> tasks = new ArrayList<>();

    public List<Task> getallTasks() {
        logger.info("Invoked the getallTasks in Service");
        return taskRepository.findAll();
    }

    public Task getTask(Integer id) {
        logger.info("Invoked the getTask in Service");
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }

    public String addTask(Task task) {
        logger.info("Invoked the addTask in Service");
        taskRepository.saveAndFlush(task);
        return "Task Added";
    }

    public String editTask(Task editTask, Integer id) {

        logger.info("Invoked the editTask in Service");

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            taskRepository.saveAndFlush(editTask);
            return "Task edited";
        } else
            return "Task does not exist";
    }

    public String deleteTask(Integer id) {
        logger.info("Invoked the deleteTask in Service");
        taskRepository.deleteById(id);
        return "Task deleted";
    }

    public String completeTask(Integer taskID){
        kafkaTemplate.send("example","complete the task "+taskID);
        return "Task posted for completion";
    }

}
