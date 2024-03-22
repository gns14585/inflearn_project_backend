package com.example.react_project_backend.repository;

import com.example.react_project_backend.domain.Todo;
import com.example.react_project_backend.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
