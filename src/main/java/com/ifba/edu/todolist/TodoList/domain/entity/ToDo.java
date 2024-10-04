package com.ifba.edu.todolist.TodoList.domain.entity;

import com.ifba.edu.todolist.TodoList.enuns.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
@Builder
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String descricao;
    @Column
    private Status status;
    @Column
    @CurrentTimestamp
    private Date data_criacao;
}
