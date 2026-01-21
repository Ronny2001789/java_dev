package com.example.demo.task;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepo;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepo.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id);
    }
}
