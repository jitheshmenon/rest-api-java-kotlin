package com.jithesh.example.controller;

import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jithesh.example.model.TodoItem;
import com.jithesh.example.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TodoController.class)
@RunWith(SpringRunner.class)
public class TodoControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TodoService todoService;

  private int counter = 0;

  private final List<TodoItem> todoItems = new ArrayList<>();


  @Before
  public void setUp() {
    Stream.of("Use Redux", "Use React", "Pass the Test")
        .map(text -> TodoItem.builder()
            .id(counter++)
            .text(text)
            .completed(false)
            .build())
        .collect(toCollection(() -> todoItems));

    when(todoService.getAllTodoItems()).thenReturn(todoItems);
  }

  @Test
  public void testClearCompleted() throws Exception {

    todoItems
           .stream()
           .filter(todo -> (todo.getId() % 2 == 0))
           .forEach(todo -> todo.setCompleted(Boolean.TRUE));
    todoItems.removeIf(TodoItem::isCompleted);


    mvc.perform(delete("/todos/clear").accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].id").value(1))
       .andExpect(jsonPath("$[0].text").value("Use React"))
       .andExpect(jsonPath("$[0].completed").value(false));

    assertThat(todoItems).hasSize(1);
  }

  @Test
  public void testCompleteAll() throws Exception {

    todoItems.forEach(todoItem -> todoItem.setCompleted(Boolean.TRUE));
    when(todoService.getAllTodoItems()).thenReturn(todoItems);

    mvc.perform(post("/todos/mark-all-complete").accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].id").value(0))
       .andExpect(jsonPath("$[0].text").value("Use Redux"))
       .andExpect(jsonPath("$[0].completed").value(true))
       .andExpect(jsonPath("$[1].id").value(1))
       .andExpect(jsonPath("$[1].text").value("Use React"))
       .andExpect(jsonPath("$[1].completed").value(true))
       .andExpect(jsonPath("$[2].id").value(2))
       .andExpect(jsonPath("$[2].text").value("Pass the Test"))
       .andExpect(jsonPath("$[2].completed").value(true));
  }

  @Test
  public void testCompleteAllWhenAllAreComplete() throws Exception {

    todoItems
           .forEach(todo -> todo.setCompleted(Boolean.FALSE));

    mvc.perform(post("/todos/mark-all-complete").accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].id").value(0))
       .andExpect(jsonPath("$[0].text").value("Use Redux"))
       .andExpect(jsonPath("$[0].completed").value(false))
       .andExpect(jsonPath("$[1].id").value(1))
       .andExpect(jsonPath("$[1].text").value("Use React"))
       .andExpect(jsonPath("$[1].completed").value(false))
       .andExpect(jsonPath("$[2].id").value(2))
       .andExpect(jsonPath("$[2].text").value("Pass the Test"))
       .andExpect(jsonPath("$[2].completed").value(false));
  }

  @Test
  public void testCompleteAllWhenSomeAreComplete() throws Exception {

    todoItems
           .forEach(todo -> todo.setCompleted(Boolean.TRUE));

    mvc.perform(post("/todos/mark-all-complete").accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].id").value(0))
       .andExpect(jsonPath("$[0].text").value("Use Redux"))
       .andExpect(jsonPath("$[0].completed").value(true))
       .andExpect(jsonPath("$[1].id").value(1))
       .andExpect(jsonPath("$[1].text").value("Use React"))
       .andExpect(jsonPath("$[1].completed").value(true))
       .andExpect(jsonPath("$[2].id").value(2))
       .andExpect(jsonPath("$[2].text").value("Pass the Test"))
       .andExpect(jsonPath("$[2].completed").value(true));
  }

  @Test
  public void testCreate() throws Exception {
    when(todoService.addTodoItem(any())).thenReturn(TodoItem.builder()
        .completed(Boolean.FALSE).id(3).text("a new todo").build());

    todoItems.add(TodoItem.builder()
        .completed(Boolean.FALSE).id(3).text("a new todo").build());

    String json = "{\"text\":\"a new todo\"}";

    mvc.perform(post("/todo").content(json)
                             .contentType(APPLICATION_JSON)
                             .accept(APPLICATION_JSON))
       .andExpect(status().isCreated())
       .andExpect(jsonPath("$.id").value(3))
       .andExpect(jsonPath("$.text").value("a new todo"))
       .andExpect(jsonPath("$.completed").value(false));

    assertThat(todoItems).hasSize(4);
  }

  @Test
  public void testDelete() throws Exception {

    String json = "{\"id\":1}";

    mvc.perform(delete("/todo/1").content(json)
                               .contentType(APPLICATION_JSON)
                               .accept(APPLICATION_JSON))
       .andExpect(status().isOk());

    assertThat(todoItems).hasSize(3);
  }

  @Test
  public void testList() throws Exception {

    mvc.perform(get("/todos").accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].id").value(0))
       .andExpect(jsonPath("$[0].text").value("Use Redux"))
       .andExpect(jsonPath("$[0].completed").value(false))
       .andExpect(jsonPath("$[1].id").value(1))
       .andExpect(jsonPath("$[1].text").value("Use React"))
       .andExpect(jsonPath("$[1].completed").value(false))
       .andExpect(jsonPath("$[2].id").value(2))
       .andExpect(jsonPath("$[2].text").value("Pass the Test"))
       .andExpect(jsonPath("$[2].completed").value(false));
  }

  @Test
  public void testMarkAsCompleteWhenComplete() throws Exception {

    when(todoService.markItemAsCompleted(anyInt())).thenReturn(TodoItem.builder()
        .completed(Boolean.TRUE).id(0).text("Use Redux").build());

    todoItems
           .stream()
           .findFirst()
           .ifPresent(todo -> todo.setCompleted(Boolean.TRUE));

    String json = "{\"id\":0}";

    mvc.perform(post("/todo/0/mark-complete").content(json)
                                      .contentType(APPLICATION_JSON)
                                      .accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(0))
       .andExpect(jsonPath("$.text").value("Use Redux"))
       .andExpect(jsonPath("$.completed").value(true));
  }

  @Test
  public void testMarkAsCompleteWhenNotComplete() throws Exception {

    when(todoService.markItemAsCompleted(anyInt())).thenReturn(TodoItem.builder()
        .completed(Boolean.TRUE).id(0).text("Use Redux").build());

    String json = "{\"id\":0}";

    mvc.perform(post("/todo/0/mark-complete").content(json)
                                      .contentType(APPLICATION_JSON)
                                      .accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(0))
       .andExpect(jsonPath("$.text").value("Use Redux"))
       .andExpect(jsonPath("$.completed").value(true));
  }

  @Test
  public void testUpdate() throws Exception {

    when(todoService.updateTodoItem(anyInt(), any())).thenReturn(TodoItem.builder()
        .completed(Boolean.FALSE).id(0).text("new text").build());

    String json = "{\"id\":0,\"text\":\"new text\"}";

    mvc.perform(put("/todo/0").content(json)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(0))
       .andExpect(jsonPath("$.text").value("new text"))
       .andExpect(jsonPath("$.completed").value(false));
  }

}
