package com.SampleApp.challenge.kotlin.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@DataJpaTest
@RunWith(SpringRunner::class)
internal class TodoRepositoryTest {

    @Autowired
    private lateinit var subject: TodoRepository

    @Test
    fun `test that repository exists`() {

        assertThat(subject.findAll()).hasSize(3)
    }
}
