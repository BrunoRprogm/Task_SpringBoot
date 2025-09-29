package com.Task_springboot.services;

import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.UserDTO;
import com.Task_springboot.models.TaskModel;
import com.Task_springboot.models.UserModel;
import com.Task_springboot.repositories.TaskRepository;
import com.Task_springboot.repositories.UserRepository;
import jakarta.persistence.Id;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    //InserirUsários
    public MensagemDTO inseriUsuario(UserDTO userDados){

        Optional<UserModel> userOP = repository.findByEmail(userDados.getEmail());
        MensagemDTO msg = new MensagemDTO();
        if (userOP.isPresent()){
            msg.setMsg("Este usuário já existe!");
            return msg;
        }else{
            UserModel usuarioModel = new UserModel();
            usuarioModel.setEmail(userDados.getEmail());
            usuarioModel.setNome(userDados.getNome());
            repository.save(usuarioModel);
            msg.setMsg("Sucesso!");
        }
        return msg;

    }
    //Listar Usuários inseridos
    public List<UserDTO> obtendoUsuarios(){

        List<UserDTO> listaUserDTO = new ArrayList<>();

        List<UserModel> listaUserModel = repository.findAll();

        for (UserModel verificando : listaUserModel){
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(verificando.getEmail());
            userDTO.setNome(verificando.getNome());
            listaUserDTO.add(userDTO);
        }
        return listaUserDTO;
    }

    //AtualizarUsários
    public MensagemDTO alterarUsuario(UserDTO userDto, String email) {
        MensagemDTO msg = new MensagemDTO();
        Optional<UserModel> userOp = repository.findByEmail(email);

        if (!userOp.isPresent()) {
            msg.setMsg("Usuário não existe no banco");
            return msg;
        }
        UserModel user = new UserModel();

        user.setNome(userDto.getNome());
        user.setEmail(userDto.getEmail());
        user.setId(userOp.get().getId());
        repository.save(user);

        msg.setMsg("Usuário alterado com sucesso");
        return msg;
    }

    //ExcluirUsuários
    public MensagemDTO deletaUsuario(String email) {
        MensagemDTO msg = new MensagemDTO();

        Optional<UserModel> userOP = repository.findByEmail(email);

        if (!userOP.isPresent()) {
           msg.setMsg("Usuário não existe!");
            return msg;
        }

        Optional<TaskModel> taskOp = taskRepository.findByUsuario(userOP.get());
        if (taskOp.isPresent()) {
            if (taskOp.get().getUsuario().equals(userOP.get())){
               msg.setMsg("Usuário vinculado em tarefas");
                return msg;
            }
        }

        repository.delete(userOP.get());
        msg.setMsg("Usuário excluído com sucesso");
        return msg;
    }

    public Object obterUsuario ( String email){
        MensagemDTO msg = new MensagemDTO();
        Optional<UserModel> userOp = repository.findByEmail(email);

        if (!userOp.isPresent()) {
            msg.setMsg("Usuário não existe no banco");
            return msg;
        }
            UserDTO userDTO = new UserDTO();
            userDTO.setNome(userOp.get().getNome());
            userDTO.setEmail(userOp.get().getEmail());
            return userDTO;
    }

}
