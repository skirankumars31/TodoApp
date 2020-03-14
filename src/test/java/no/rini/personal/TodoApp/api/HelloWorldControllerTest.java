package no.rini.personal.TodoApp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HelloWorldControllerTest {

    @InjectMocks
    HelloWorldController helloWorldController;
    GsonBuilder gsonBuilder;
    Gson gson;
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private TaskService taskService;

    @BeforeEach
    void beforeEach() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    @Test
    void sayHello_should_return_correct_string() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/sayHello/Kiran",
            String.class)).contains("Hello Kiran");
    }

    @Test
    void check_if_task_is_added_successfully() throws Exception {
        //Arrange
        Task write = new Task(1, "Write Book", "Write about a subject you like");
        Mockito.when(taskService.addTask(Mockito.any(Task.class))).thenReturn("Task Added");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/addTask").accept(MediaType.APPLICATION_JSON).content(gson.toJson(write)).contentType(MediaType.APPLICATION_JSON);

        //Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        JSONAssert.assertEquals("200", String.valueOf(result.getResponse().getStatus()), false);
    }

    @Test
    void check_if_task_is_edited_successfully() throws Exception {
        //Arrange
        Task write = new Task(1, "Write Book", "Write about a subject you like");
        Mockito.when(taskService.editTask(Mockito.any(Task.class), Mockito.anyInt())).thenReturn("Task Edited");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/editTask/{id}", 1).accept(MediaType.APPLICATION_JSON).content(gson.toJson(write)).contentType(MediaType.APPLICATION_JSON);

        //Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        JSONAssert.assertEquals("200", String.valueOf(result.getResponse().getStatus()), false);
    }

    @Test
    void check_if_task_is_deleted_successfully() throws Exception {
        //Arrange
        Mockito.when(taskService.deleteTask(Mockito.anyInt())).thenReturn("Task Edited");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/deleteTask/{id}", 1).accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        JSONAssert.assertEquals("200", String.valueOf(result.getResponse().getStatus()), false);
    }

    @Test
    void check_if_get_task_is_successful() throws Exception {
        //Arrange
        Task write = new Task(1, "Write Book", "Write about a subject you like");
        Mockito.when(taskService.getTask(Mockito.anyInt())).thenReturn(write);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/getTask/{id}", 1).accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        JSONAssert.assertEquals(gson.toJson(write), result.getResponse().getContentAsString(), false);
    }


    @Test
    void test_if_getallTasks_returns_all_tasks() throws Exception {
        //Arrange
        ArrayList<Task> tasks = new ArrayList<>();
        Task write = new Task(1, "Write Book", "Write about a subject you like");
        tasks.add(write);
        Mockito.when(taskService.getallTasks()).thenReturn(tasks);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/getallTasks").accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        JSONAssert.assertEquals(gson.toJson(tasks), result.getResponse().getContentAsString(), false);
    }
}
