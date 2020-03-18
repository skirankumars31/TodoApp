package no.rini.personal.TodoApp.service;

import no.rini.personal.TodoApp.model.Task;
import no.rini.personal.TodoApp.repository.TaskRepository;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
    partitions = 1,
    controlledShutdown = false,
    topics = "example",
    brokerProperties = {
    "listeners=PLAINTEXT://localhost:9092",
    "port=9092"
    })
class TaskServiceTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

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

    @Test
    void check_if_task_is_posted_to_kafka() {

        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(singleton("example"));

        taskService.completeTask(1);

        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "example",10);
        consumer.close();
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.value()).isEqualTo("complete the task 1");
    }
}
