package no.rini.personal.TodoApp.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping(value = "/sayHello/{name}",produces = "application/json")
    public @ResponseBody
    String sayHello(@PathVariable("name") String name) {
        return "Hello " + name;
    }

    @PostMapping(value = "/addTask",produces = "application/json",consumes = "text/plain")
    public @ResponseBody String addTask(@RequestBody String name) {
        return "The name in the post request is "+name;
    }
}
