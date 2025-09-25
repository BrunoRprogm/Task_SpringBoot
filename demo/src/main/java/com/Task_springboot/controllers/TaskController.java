package com.Task_springboot.controllers;

import com.Task_springboot.services.TaskService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Tarefas")
public class TaskController {

    //Injeção de depêndencia - CHECK
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
