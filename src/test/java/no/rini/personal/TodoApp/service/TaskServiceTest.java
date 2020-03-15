package no.rini.personal.TodoApp.service;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void check_if_task_is_found_by_id() {
        Task task = new Task(4, "Cook food", "Cook food that you like");
        when(taskRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(task));
        Task foundtask = taskService.getTask(4);
        assertThat(foundtask).isNotNull();
    }
}
