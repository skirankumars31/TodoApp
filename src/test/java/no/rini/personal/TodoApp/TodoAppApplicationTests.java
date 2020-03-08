package no.rini.personal.TodoApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void passTest() {
		assertEquals(true,true);
	}

	@Test
	void failTest() {
		assertEquals(true,false);
	}



}
