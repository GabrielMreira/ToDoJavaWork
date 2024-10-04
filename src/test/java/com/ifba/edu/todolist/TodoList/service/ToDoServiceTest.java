package com.ifba.edu.todolist.TodoList.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ifba.edu.todolist.TodoList.Builder.ToDoBuilder;
import com.ifba.edu.todolist.TodoList.domain.dto.ToDoDTO;
import com.ifba.edu.todolist.TodoList.domain.entity.ToDo;
import com.ifba.edu.todolist.TodoList.enuns.Status;
import com.ifba.edu.todolist.TodoList.exceptions.NotFoundException;
import com.ifba.edu.todolist.TodoList.mapper.ToDoMapper;
import com.ifba.edu.todolist.TodoList.repository.ToDoRepository;
import com.ifba.edu.todolist.TodoList.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


//@ContextConfiguration(classes = {ToDoServiceImpl.class, ToDoMapperImpl.class})
@ExtendWith(SpringExtension.class)
public class ToDoServiceTest {
    @InjectMocks
    private ToDoServiceImpl service;
    @Mock
    private ToDoMapper mapper;
    @Mock
    private ToDoRepository repository;

    @Test
    void deveBuscarTodas() throws Exception {
        var todo = ToDoBuilder.buildToDo();
        given(repository.findAll()).willReturn(List.of(todo));
        given(mapper.toToDoDTO(any(ToDo.class))).willReturn(new ToDoDTO(1L, "tarefa 1", "tarefa descrição 1", Status.PARA_FAZER, Date.valueOf(LocalDate.of(2024, 8, 18))));

        List<ToDoDTO> response = service.findAll();
        var task = response.get(0);
        compararTarefa(task, todo);
    }

    void compararTarefa(ToDoDTO task, ToDo taskMock){
        assertEquals(task.getId(), 1L);
        assertEquals(task.getTitulo(), "tarefa 1");
        assertEquals(task.getDescricao(), "tarefa descrição 1");
        assertEquals(task.getStatus(), Status.PARA_FAZER);
        assertEquals(task.getData_criacao(), Date.valueOf(LocalDate.of(2024, 8, 18)));
    }
    @Test
    void deveBuscarTarefaPorId() throws Exception{
        var toDo = ToDoBuilder.buildToDo();
        given(repository.findById(1L)).willReturn(Optional.of(toDo));
        given(mapper.toToDoDTO(any(ToDo.class))).willReturn(new ToDoDTO(1L, "tarefa 1", "tarefa descrição 1", Status.PARA_FAZER, Date.valueOf(LocalDate.of(2024, 8, 18))));
        compararTarefa(service.findById(1L),toDo);
    }

    @Test
    void deveNaoAcharTarefaPorId() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.empty());
        var toDo = service.findById(1L);
        assertEquals(toDo, null);
    }

    @Test
    void deveRemoverTarefa() throws Exception {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);
        given(repository.existsById(id)).willReturn(true);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void deveGerarErrorAoTentarDeletarTarefa() throws Exception {
        Long id = 1L;
        given(repository.existsById(id)).willReturn(false);
        assertThrows(NotFoundException.class, () -> service.delete(id));
    }

}
