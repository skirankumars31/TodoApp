package no.rini.personal.TodoApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Task {

    @NotNull
    @Id
    private Integer id;
    private String title;
    private String description;

    public Task() {
    }

    public Task(@NotNull Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
