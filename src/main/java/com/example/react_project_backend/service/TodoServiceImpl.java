package com.example.react_project_backend.service;

import com.example.react_project_backend.domain.Todo;
import com.example.react_project_backend.dto.TodoDTO;
import com.example.react_project_backend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    // todoRepository 가 없으면 제대로 동작을 할 수 없으니 의존성 주입
    private final TodoRepository todoRepository;

    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno); // Optional<Todo>를 통해 조회
        // orElseThrow() => 문제가 생기면 예외를 보내고, 이상이 없으면 정상실행
        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }

    // 등록
    @Override
    public Long register(TodoDTO dto) {

        Todo todo = dtoToEntity(dto);
        Todo result = todoRepository.save(todo);

        return result.getTno();
    }

    // 변경
    @Override
    public void modify(TodoDTO dto) {
        Optional<Todo> result = todoRepository.findById(dto.getTno()); // DB에 있는 데이터를 가져오기
        Todo todo = result.orElseThrow();

        // DB에서 가져온 데이터를 변경하기
        todo.changeTitle(dto.getTitle());
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete());
        todo.changeDueDate(dto.getDueDate());

        // 저장
        todoRepository.save(todo);
    }

    // 삭제
    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }
}
