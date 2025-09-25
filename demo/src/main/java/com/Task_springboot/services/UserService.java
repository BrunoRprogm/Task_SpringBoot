package com.Task_springboot.services;

import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.UserDTO;
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
    private final TaskRepository repositoryCategoria;
    private final TaskRepository taskRepository;

    public UserService(UserRepository repository, TaskRepository repositoryCategoria, TaskRepository taskRepository) {
        this.repository = repository;
        this.repositoryCategoria = repositoryCategoria;
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

    //ExcluirUsuários
   // public MensagemDTO excluir (UserDTO userDados){
     //   Optional<UserModel> userOP = repository.findByEmail(userDados.getEmail());
    //    MensagemDTO msg = new MensagemDTO();


   //     if (userOP.isEmpty()){
          //  msg.setMsg("user não encontrado");
       // return msg;

      //  UserModel userModel = userOP.get();

       // if (userModel.get)







}
