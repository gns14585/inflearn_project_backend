package com.example.react_project_backend.repository.search;

import com.example.react_project_backend.domain.QTodo;
import com.example.react_project_backend.domain.Todo;
import com.example.react_project_backend.dto.PageRequestDTO;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {
        log.info("search1...................");
        QTodo todo = QTodo.todo; // 쿼리를 날리기 위한 객체
        JPQLQuery<Todo> query = from(todo); // Todo에 관련된 쿼리를 만들고 todo에 있는 데이터를 뽑아냄

        // 페이징처리
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        // QueryDsl 을 가져오고, pageable과 query 를 applyPagination 로 가져오기.
        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch(); // 쿼리를 실행함 (목록 데이터 가져올때)
        long total = query.fetchCount(); // fetchCount는 전부 Long타입으로 나옴. (데이터가 많아서)

        return new PageImpl<>(list, pageable, total);

        // Querydsl의 목적은 동적 데이터를 처리하는것 (동적인 쿼리를 만드는것)
    }
}
