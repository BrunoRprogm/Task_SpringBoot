package com.Task_springboot.controllers;
import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.TaskDTO;
import com.Task_springboot.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Tarefas")
public class TaskController {

    //Injeção de depêndencia - CHECK
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //Listando tarefas
    @GetMapping("/listarTarefas") //OBject?
    public ResponseEntity<List<TaskDTO>> obterTarefas() {
        List<TaskDTO> listaTarefas = taskService.obterTarefas();

        if (listaTarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listaTarefas);
        }
        return ResponseEntity.ok().body(listaTarefas);
    }

    @PostMapping("/criandoTarefa")
    public ResponseEntity<MensagemDTO> criandoTarefa(@RequestBody @Valid TaskDTO taskDTO) {
        MensagemDTO msg = taskService.inserirTarefa(taskDTO);

        if (msg.getMsg().equals("Usuário não existe!")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.getMsg().equals("Usuário já possui agenda para a data informada!")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        //  if (msg.equals("erro!")){
        //     return ResponseEntity.status(HttpStatus.FOUND).body(msg);
        //   }

        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> excluirTask(@PathVariable Long id){
        MensagemDTO msg = taskService.deletandoTarefa(id);

        if (msg.getMsg().equals("Tarefa não encontrada!")){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return   ResponseEntity.ok().body(msg);
    }

    @PutMapping("{id}")
    public ResponseEntity<MensagemDTO> atualizarTask(@PathVariable Long id, @RequestBody @Valid TaskDTO task){
        MensagemDTO msg = taskService.atualizarTarefa(task,id);

        if (msg.getMsg().equals("Tarefa não existe!")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

        if (msg.getMsg().equals("Usuário não existe!")){
            return    ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

        if (msg.getMsg().equals("Já existe uma tarefa para essa data!")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }

        return ResponseEntity.ok().body(msg);
    }
}

