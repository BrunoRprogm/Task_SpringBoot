package com.Task_springboot.controllers;

import com.Task_springboot.dtos.MensagemDTO;
import com.Task_springboot.dtos.UserDTO;
import com.Task_springboot.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
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
            List<UserDTO> listUser = service.obtendoUsuarios();

            if (listUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listUser);
            }
            return ResponseEntity.ok(listUser);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity <MensagemDTO> excluirUsuario(@PathVariable String email){
        MensagemDTO msg = service.deletaUsuario(email);

        if (msg.getMsg().equals("Usuário não existe!")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);

        }if (msg.getMsg().equals("Usuário vinculado em tarefas")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return  ResponseEntity.ok(msg);

    }

    @PutMapping("/{email}")
    public  ResponseEntity <MensagemDTO> alterarUsuario(@RequestBody @Valid UserDTO userDados,@PathVariable String email){
        MensagemDTO msg = service.alterarUsuario(userDados,email);

        if (msg.getMsg().equals("Usuário não existe no banco")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return  ResponseEntity.ok(msg);

    }

    @GetMapping("/{email}")
    public  ResponseEntity<Object> obtendoUsuario (@PathVariable String email){
        Object userDto = service.obterUsuario(email);

        if (userDto instanceof MensagemDTO){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userDto);
        }
        return ResponseEntity.ok(userDto);
    }


}






