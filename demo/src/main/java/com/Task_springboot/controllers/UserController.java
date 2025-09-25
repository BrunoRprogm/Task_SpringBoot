package com.Task_springboot.controllers;

import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.UserDTO;
import com.Task_springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuario")
public class UserController {
    //Injeção de depêndencia - CHECK
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/inseriUsuario")
    public ResponseEntity<MensagemDTO> inseriUsuario(@RequestBody @Valid UserDTO userDados){

        MensagemDTO msg = service.inseriUsuario(userDados);

        if(msg.getMsg().equals("Sucesso!")){
            msg.setMsg("Usuário Inserido com sucesso!");
            return ResponseEntity.ok().body(msg);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
    }

    @GetMapping("/listandoUsuario")
    public ResponseEntity<List<UserDTO>> obterUsuarios(){
        return ResponseEntity.ok().body(service.obtendoUsuarios());
    }


}





 //  @DeleteMapping("/deletarUsuario")
   // public ResponseEntity<MensagemDTO> excluirUsuario(@PathVariable Long id){
      //  MensagemDTO msg = service.excluir(id);
     //  if (msg.getMsg().equals("sucesso")){
      //      msg.setMsg("Usuário excluido com sucesso!");
         //  return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
      //  }else{







