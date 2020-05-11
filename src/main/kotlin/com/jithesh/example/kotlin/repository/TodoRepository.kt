package com.SampleApp.challenge.kotlin.repository

import com.SampleApp.challenge.kotlin.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository

internal interface TodoRepository: JpaRepository<Todo, Int>
