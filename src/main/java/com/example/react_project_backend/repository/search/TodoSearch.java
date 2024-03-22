package com.example.react_project_backend.repository.search;

import com.example.react_project_backend.domain.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {
    Page<Todo> search1();
}
