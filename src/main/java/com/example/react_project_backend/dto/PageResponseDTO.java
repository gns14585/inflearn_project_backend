package com.example.react_project_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> { // 페이징 결과물

    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;


    // 페이징 처리
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        // 끝페이지 계산 end
        // 현재페이지번호
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        // 시작페이지번호
        int start = end - 9;

        // 진짜 마지막페이지 계산
        int last = (int)(Math.ceil(totalCount / (double) pageRequestDTO.getSize()));

        end = end > last ? last : end;
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        this.prevPage = prev ? start - 1 : 0; // 이전페이지 관련
        this.nextPage = next ? end + 1 : 0; // 다음페이지 관련


    }

}
