package com.jithesh.example.repository;

import com.jithesh.example.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
  List<Todo> findTodosById(int id);
}
