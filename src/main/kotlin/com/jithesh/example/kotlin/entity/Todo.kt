package com.SampleApp.challenge.kotlin.entity

import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "todos")
data class Todo (

    @field:Column(nullable = false)
    var completed: Boolean = false,

    @field:Column(nullable = false)
    var text: String = ""

) : AbstractPersistable<Int>()
