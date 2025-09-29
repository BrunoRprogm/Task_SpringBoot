package com.Task_springboot.services;

import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.TaskDTO;
import com.Task_springboot.dtos.UserDTO;
import com.Task_springboot.models.TaskModel;
import com.Task_springboot.models.UserModel;
import com.Task_springboot.repositories.TaskRepository;
import com.Task_springboot.repositories.UserRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    private final UserRepository userRepository;

    public TaskService(TaskRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //ObterTarefas
        public List<TaskDTO> obterTarefas(){
            List<TaskDTO> listaTaskDTO = new ArrayList<>();
            List<TaskModel> listaTaskModel = repository.findAll();

            for (TaskModel verificando : listaTaskModel){
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setDataAgendamento(verificando.getData());
                taskDTO.setEmailUsuario(verificando.getUsuario().getEmail());
                taskDTO.setDescricao(verificando.getDescricao());
                taskDTO.setStatus(verificando.getStatus());
                taskDTO.setTitulo(verificando.getTitulo());
                listaTaskDTO.add(taskDTO);
            }
            return listaTaskDTO;
        }
    //InserirTarefas
    public MensagemDTO inserirTarefa(TaskDTO task){
    MensagemDTO msg = new MensagemDTO();


    Optional<UserModel> userOP = userRepository.findByEmail(task.getEmailUsuario());
    if (!userOP.isPresent()){
        msg.setMsg("Usuário não existe!");
        return msg;
    }
    Optional<TaskModel> taskOP = repository.findByUsuarioEmailAndData(task.getEmailUsuario(), task.getDataAgendamento());
    if (taskOP.isPresent() ){
        msg.setMsg("Usuário já possui agenda para a data informada!");
        return msg;
    }


    TaskModel taskModel = new TaskModel();
    taskModel.setTitulo(task.getTitulo());
    taskModel.setDescricao(task.getDescricao());
    taskModel.setData(task.getDataAgendamento());
    taskModel.setStatus(task.getStatus());
    taskModel.setUsuario(userOP.get());
    repository.save(taskModel);
    msg.setMsg("Tarefa Criada!");

    return msg;
    }


    //excluirTarefas
    public MensagemDTO deletandoTarefa (Long id){
        MensagemDTO msg = new MensagemDTO();
        Optional<TaskModel> taskOP = repository.findById(id);
        if (!taskOP.isPresent()){
            msg.setMsg("Tarefa não encontrada!");
            return msg;
        }
        repository.deleteById(id);
        msg.setMsg("Tarefa excluida!");
        return msg;
    }



   // atualizaTarefas
    public MensagemDTO atualizarTarefa(TaskDTO task, long id){
        MensagemDTO msg = new MensagemDTO();

        Optional<UserModel> userOP = userRepository.findByEmail(task.getEmailUsuario());
        if (!userOP.isPresent()){
            msg.setMsg("Usuário não existe!");
            return msg;
        }

        Optional<TaskModel> TaskOp = repository.findById(id);
        if (!TaskOp.isPresent()){
            msg.setMsg("Tarefa não existe!");
            return msg;
        }

        Optional<TaskModel> conflitoDatas = repository.findByUsuarioEmailAndData(userOP.get().getEmail(),task.getDataAgendamento());

        if (conflitoDatas.isPresent() && !conflitoDatas.get().getId().equals(id)){
            msg.setMsg("Já existe uma tarefa para essa data!");
            return msg;
        }

        TaskModel taskModel = TaskOp.get();

        taskModel.setTitulo(task.getTitulo());
        taskModel.setDescricao(task.getDescricao());
        taskModel.setData(task.getDataAgendamento());
        taskModel.setStatus(task.getStatus());
        taskModel.setUsuario(userOP.get()); //Atualiza o usuário ?
        repository.save(taskModel);

        msg.setMsg("Tarefa Atualizada!");
        return msg;
    }
}
