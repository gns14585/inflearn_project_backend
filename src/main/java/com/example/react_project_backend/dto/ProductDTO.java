package com.example.react_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    // 삭제는 절대 하면안됨 컬럼을 하나 추가해서 관리해야하고, 숨김처리 해야될듯

    private Long pno; // id
    private String pname; // 이름
    private int price; // 가격
    private String pdesc; // 설명
    private boolean delFlag;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); // 파일 업로드 용

    @Builder.Default
    private List<String> uploadedFileNames = new ArrayList<>(); // 기존에 사용했던 파일 이름들을 조회하는 용으로 사용

}
