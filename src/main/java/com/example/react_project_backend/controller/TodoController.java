package com.example.react_project_backend.controller;

import com.example.react_project_backend.dto.PageRequestDTO;
import com.example.react_project_backend.dto.PageResponseDTO;
import com.example.react_project_backend.dto.TodoDTO;
import com.example.react_project_backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    // 특정 데이터 가져오기
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {
        return todoService.get(tno);
    }

    // 전체 데이터 가져오기
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("list............" + pageRequestDTO);
        return todoService.getList(pageRequestDTO);
    }

    // 데이터 등록
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO dto) {
        log.info("todoDto: " + dto);
        Long tno = todoService.register(dto);
        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno,
                                      @RequestBody TodoDTO todoDTO) {
        todoDTO.setTno(tno);
        todoService.modify(todoDTO);
        return Map.of("RESULT","SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable Long tno) {
        todoService.remove(tno);
        return Map.of("RESULT","SUCCESS");
    }














}
