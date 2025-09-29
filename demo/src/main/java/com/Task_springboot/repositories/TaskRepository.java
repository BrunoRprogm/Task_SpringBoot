package com.Task_springboot.repositories;

import com.Task_springboot.models.TaskModel;
import com.Task_springboot.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel,Long> {


    Optional<TaskModel> findByUsuario(UserModel emailUsuario);
    Optional<TaskModel> findByUsuarioEmailAndData(String email, LocalDate dataAgendamento);
    //Optional<UserModel> findByEmail(UserModel emailUsuario);
}
//Como eu preciso validar o usuário por e-mail, o próprio JPA indica que você crie esse metodo.