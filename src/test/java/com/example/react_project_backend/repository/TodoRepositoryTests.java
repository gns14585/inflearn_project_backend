package com.example.react_project_backend.repository;

import com.example.react_project_backend.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {
        Assertions.assertNotNull(todoRepository);
        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void testInsert() {
        for (int i = 0; i < 100; i++) {
            Todo todo = Todo.builder()
                    .title("Title" + i)
                    .content("Content..." + i)
                    .dueDate(LocalDate.of(2023, 12, 30))
                    .build();

            Todo result = todoRepository.save(todo);

            log.info(result);
        }
    }

    @Test
    public void testRead() {
        Long tno = 1L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }

    @Test
    public void testUpdate() {
        // 먼저 로딩 하고 엔티티 객체를 변경 / setter를 사용하게됨. 단어는 setter대신 change를 사용함
        Long tno = 1L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        todo.changeTitle("Update Title"); // 컬럼에 있는 Title 내용을 변경
        todo.changeContent("Updated content"); // 컬럼에 잇는 content 내용을 변경
        todo.changeComplete(true); // 컬럼에 있는 complete 를 변경

        todoRepository.save(todo); // 저장
    }

    // 페이징 처리 테스트
    @Test
    public void testPaging() {
        // 페이지 번호는 0부터 시작
        // descending() => 내림차순 , ascending() => 오름차순
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);
        log.info(result.getTotalElements());
        log.info(result.getContent());
    }

//    @Test
//    public void testSearch1() {
//        todoRepository.search1();
//    }

}
