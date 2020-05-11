package com.jithesh.example.repository;

import com.jithesh.example.entity.TodoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<TodoCategory, Integer> {
  TodoCategory findCategoryById(int id);
}
