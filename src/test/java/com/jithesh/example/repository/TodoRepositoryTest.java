package com.jithesh.example.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TodoRepositoryTest {

  @Autowired
  private TodoRepository subject;

  @Test
  public void testRepositoryIsPopulated() {

    Assertions.assertThat(subject.findAll()).hasSize(3);
  }

}
