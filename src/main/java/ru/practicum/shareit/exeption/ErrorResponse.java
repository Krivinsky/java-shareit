package ru.practicum.shareit.exeption;

public class ErrorResponse {
    String name;
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ErrorResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
