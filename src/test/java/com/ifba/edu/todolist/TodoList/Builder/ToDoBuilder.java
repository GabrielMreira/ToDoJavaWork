package com.ifba.edu.todolist.TodoList.Builder;

import com.ifba.edu.todolist.TodoList.domain.entity.ToDo;
import com.ifba.edu.todolist.TodoList.enuns.Status;

import java.sql.Date;
import java.time.LocalDate;

public final class ToDoBuilder {

    private ToDoBuilder() {};

    public static ToDo buildToDo(){
        return ToDo
                .builder()
                .id(1L)
                .titulo("tarefa 1")
                .descricao("tarefa descrição 1")
                .status(Status.PARA_FAZER)
                .data_criacao(Date.valueOf(LocalDate.of(2024, 8, 18)))
                .build();
    }
}
