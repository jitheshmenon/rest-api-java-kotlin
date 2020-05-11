package com.SampleApp.challenge.kotlin.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
@CrossOrigin
class TodoController {

    internal companion object {
        const val COMPLETED = "completed"
        const val ID = "id"
        const val TEXT = "text"
    }

    internal val todos: MutableList<MutableMap<String, Any?>> = mutableListOf()

    private var counter = 0

    @RequestMapping(path = arrayOf("/todos/clear"), method = arrayOf(RequestMethod.POST))
    fun clearCompleted(): List<Map<String, Any?>> =
        todos.apply { removeIf { todo -> todo[COMPLETED] == true } }

    @RequestMapping(path = arrayOf("/todos/complete"), method = arrayOf(RequestMethod.POST))
    fun completeAll(): List<Map<String, Any?>> {

        val complete = todos.map { todo -> todo[COMPLETED] }
            .any { completed -> completed == false }

        return todos.apply {
            forEach { todo -> todo.put(COMPLETED, complete) }
        }
    }

    @RequestMapping(path = arrayOf("/todo"), method = arrayOf(RequestMethod.POST))
    fun create(@RequestBody body: Map<String, Any?>): Map<String, Any?> =
        mutableMapOf(ID to counter++,
                     COMPLETED to false,
                     TEXT to body[TEXT])
            .also { todos.add(it) }

    @RequestMapping(path = arrayOf("/todo"), method = arrayOf(RequestMethod.DELETE))
    fun delete(@RequestBody body: Map<String, Any?>) {
        body[ID]?.let { id -> todos.removeIf { id == it[ID] } }
    }

    @PostConstruct
    fun init() {
        todos.clear()
        counter = 0
        listOf("Use Redux", "Use React", "Pass the Test")
            .map { mutableMapOf<String, Any?>(ID to counter++,
                                              COMPLETED to false,
                                              TEXT to it) }
            .forEach { todos.add(it) }
    }

    @RequestMapping(path = arrayOf("/todos"), method = arrayOf(RequestMethod.GET))
    fun list(): List<Map<String, Any?>> =
        todos

    @RequestMapping(path = arrayOf("/todo/complete"), method = arrayOf(RequestMethod.POST))
    fun markAsComplete(@RequestBody body: Map<String, Any?>): Map<String, Any?>? =
        findById(body[ID] as Int?)
            ?.also {
                val completed = it[COMPLETED] as Boolean
                it[COMPLETED] = !completed
            }

    @RequestMapping(path = arrayOf("/todo"), method = arrayOf(RequestMethod.PUT))
    fun update(@RequestBody body: Map<String, Any?>): Map<String, Any?>? =
        findById(body[ID] as Int?)
            ?.also { it[TEXT] = body[TEXT] }

    private fun findById(id: Int?): MutableMap<String, Any?>? =
        when (id) {
            null -> null
            else -> todos.find { id == it[ID] }
        }

}
