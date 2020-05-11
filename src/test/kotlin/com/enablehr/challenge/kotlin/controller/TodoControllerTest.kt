package com.SampleApp.challenge.kotlin.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TodoController::class)
@RunWith(SpringRunner::class)
internal class TodoControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var subject: TodoController

    @Before
    fun setUp() {
        // Reset the subject state between tests
        subject.init()
    }

    @Test
    fun `test that POST to "todos-clear" without a body clears todos`() {

        subject.todos.filter { it[TodoController.ID] as Int % 2 == 0 }
            .forEach { it[TodoController.COMPLETED] = true }

        mvc.perform(post("/todos/clear").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].text").value("Use React"))
            .andExpect(jsonPath("$[0].completed").value(false))

        assertThat(subject.todos).hasSize(1)
    }

    @Test
    fun `test that POST to "todos-complete" without a body completes all todos`() {

        mvc.perform(post("/todos/complete").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(0))
            .andExpect(jsonPath("$[0].text").value("Use Redux"))
            .andExpect(jsonPath("$[0].completed").value(true))
            .andExpect(jsonPath("$[1].id").value(1))
            .andExpect(jsonPath("$[1].text").value("Use React"))
            .andExpect(jsonPath("$[1].completed").value(true))
            .andExpect(jsonPath("$[2].id").value(2))
            .andExpect(jsonPath("$[2].text").value("Pass the Test"))
            .andExpect(jsonPath("$[2].completed").value(true))
    }

    @Test
    fun `test that POST to "todos-complete" without a body uncompletes all todos when they are complete`() {

        subject.todos.forEach { it[TodoController.COMPLETED] = true }

        mvc.perform(post("/todos/complete").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(0))
            .andExpect(jsonPath("$[0].text").value("Use Redux"))
            .andExpect(jsonPath("$[0].completed").value(false))
            .andExpect(jsonPath("$[1].id").value(1))
            .andExpect(jsonPath("$[1].text").value("Use React"))
            .andExpect(jsonPath("$[1].completed").value(false))
            .andExpect(jsonPath("$[2].id").value(2))
            .andExpect(jsonPath("$[2].text").value("Pass the Test"))
            .andExpect(jsonPath("$[2].completed").value(false))
    }

    @Test
    fun `test that POST to "todos-complete" without a body completes all todos when some are incomplete`() {

        subject.todos.filter { it[TodoController.ID] as Int % 2 == 0 }
            .forEach { it[TodoController.COMPLETED] = true }

        mvc.perform(post("/todos/complete").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(0))
            .andExpect(jsonPath("$[0].text").value("Use Redux"))
            .andExpect(jsonPath("$[0].completed").value(true))
            .andExpect(jsonPath("$[1].id").value(1))
            .andExpect(jsonPath("$[1].text").value("Use React"))
            .andExpect(jsonPath("$[1].completed").value(true))
            .andExpect(jsonPath("$[2].id").value(2))
            .andExpect(jsonPath("$[2].text").value("Pass the Test"))
            .andExpect(jsonPath("$[2].completed").value(true))
    }

    @Test
    fun `test that POST to "todo" with a body creates a new todo`() {

        // language=JSON
        val json = """
            {
              "text": "a new todo"
            }
            """

        mvc.perform(post("/todo").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.text").value("a new todo"))
            .andExpect(jsonPath("$.completed").value(false))

        assertThat(subject.todos).hasSize(4)
    }

    @Test
    fun `test that DELETE to "todo" with a body deletes a todo`() {

        // language=JSON
        val json = """
            {
              "id": 1
            }
            """

        mvc.perform(delete("/todo").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
            .andExpect(status().isOk)

        assertThat(subject.todos).hasSize(2)
    }

    @Test
    fun `test that GET to "todos" without a body lists todos`() {

        mvc.perform(get("/todos").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(0))
            .andExpect(jsonPath("$[0].text").value("Use Redux"))
            .andExpect(jsonPath("$[0].completed").value(false))
            .andExpect(jsonPath("$[1].id").value(1))
            .andExpect(jsonPath("$[1].text").value("Use React"))
            .andExpect(jsonPath("$[1].completed").value(false))
            .andExpect(jsonPath("$[2].id").value(2))
            .andExpect(jsonPath("$[2].text").value("Pass the Test"))
            .andExpect(jsonPath("$[2].completed").value(false))
    }

    @Test
    fun `test that POST to "todo-complete" with a body uncompletes a todo`() {

        subject.todos.first()
            .let { it[TodoController.COMPLETED] = true }

        // language=JSON
        val json = """
            {
              "id": 0
            }
            """

        mvc.perform(post("/todo/complete").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(0))
            .andExpect(jsonPath("$.text").value("Use Redux"))
            .andExpect(jsonPath("$.completed").value(false))
    }

    @Test
    fun `test that POST to "todo-complete" with a body completes a todo`() {

        // language=JSON
        val json = """
            {
              "id": 0
            }
            """

        mvc.perform(post("/todo/complete").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(0))
            .andExpect(jsonPath("$.text").value("Use Redux"))
            .andExpect(jsonPath("$.completed").value(true))
    }

    @Test
    fun `test that PUT to "todo" with a body updates a todo`() {

        // language=JSON
        val json = """
            {
              "id": 0,
              "text": "new text"
            }
            """

        mvc.perform(put("/todo").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(0))
            .andExpect(jsonPath("$.text").value("new text"))
            .andExpect(jsonPath("$.completed").value(false))
    }

}
