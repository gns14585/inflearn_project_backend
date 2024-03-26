package com.example.react_project_backend.service;

import com.example.react_project_backend.domain.Todo;
import com.example.react_project_backend.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {
    TodoDTO get(Long tno); // 조회한다고 했을때 파라미터는 Todo의 id , 리턴타입은 DTO 여야함.

    // 등록
    Long register(TodoDTO dto);

    // 변경
    void modify(TodoDTO dto);

    // 삭제
    void remove(Long tno);

    // Todo Entity를 DTO로 바꿔줌
    default TodoDTO entityToDTO(Todo todo) {
        return TodoDTO.builder()
                        .tno(todo.getTno())
                        .title(todo.getTitle())
                        .content(todo.getContent())
                        .complete(todo.isComplete()) // boolean 타입은 get 이 아닌 is 사용
                        .dueDate(todo.getDueDate())
                        .build();
    }

    // Todo DTO를 Entity로 바꿔줌
    default Todo dtoToEntity(TodoDTO todoDTO) {
        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete()) // boolean 타입은 get 이 아닌 is 사용
                .dueDate(todoDTO.getDueDate())
                .build();
    }
}
