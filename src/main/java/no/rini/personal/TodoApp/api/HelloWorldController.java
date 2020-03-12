package no.rini.personal.TodoApp.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping(value = "/sayHello/{name}", produces = "application/json")
    public @ResponseBody
    String sayHello(@PathVariable("name") String name) {
        return "Hello " + name;
    }

}
