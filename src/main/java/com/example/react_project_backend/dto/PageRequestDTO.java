package com.example.react_project_backend.dto;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder // 상속을 해야할 경우
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

}
