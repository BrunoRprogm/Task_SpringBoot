package com.Task_springboot.dtos;

import com.Task_springboot.models.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskDTO {

    Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    @NotNull
    private LocalDate dataAgendamento;

    @NotNull
    private Status status;



    @NotNull @Email
    private String emailUsuario;


    public TaskDTO(Long id, String titulo, String descricao, LocalDate dataAgendamento, Status status, String emailUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
        this.emailUsuario = emailUsuario;
    }

    public TaskDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataAgendamento=" + dataAgendamento +
                ", status=" + status +
                ", emailUsuario='" + emailUsuario + '\'' +
                '}';
    }
}

