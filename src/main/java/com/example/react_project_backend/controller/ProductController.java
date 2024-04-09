package com.example.react_project_backend.controller;

import com.example.react_project_backend.dto.PageRequestDTO;
import com.example.react_project_backend.dto.PageResponseDTO;
import com.example.react_project_backend.dto.ProductDTO;
import com.example.react_project_backend.service.ProductService;
import com.example.react_project_backend.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

//    @PostMapping("/")
//    public Map<String, String> register(ProductDTO productDTO) {
//
//        log.info("register: " + productDTO);
//        List<MultipartFile> files = productDTO.getFiles();
//        List<String> uploadedFileNames = fileUtil.saveFiles(files);
//        productDTO.setUploadFileNames(uploadedFileNames);
//        log.info(uploadedFileNames);
//
//        return Map.of("RESULT", "SUCCESS");
//    }

    // ----------------------- 파일 조회 ----------------------
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {

        return fileUtil.getFile(fileName);
    }

    // ----------------------- 목록데이터 조회 -----------------------
    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.getList(pageRequestDTO);
    }

    // ----------------------- 파일 업로드 ----------------------
    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {
        List<MultipartFile> files = productDTO.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long pno = productService.register(productDTO);
        return Map.of("result", pno);

    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno) {
        return productService.get(pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable Long pno, ProductDTO productDTO) {
        productDTO.setPno(pno);

        // old product (원래 상품 가져오기)
        ProductDTO oldProductDTO = productService.get(pno);

        // file upload (새로 업로드하는 파일)
        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        // keep files (지워지지 않은 파일들)
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        // 파일목록 만들어주기
        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDTO);

        // 기존 파일 삭제
        List<String> oldFileNames = oldProductDTO.getUploadFileNames();
        if (oldFileNames != null && !oldFileNames.isEmpty()) {
            // 원래는 있었는데 새로 업로드된 파일도 아니고, 과거에 업로드 했던 파일도 아닐때
            List<String> removeFiles = oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            fileUtil.deleteFiles(removeFiles);

        }
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable Long pno) {
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();
        productService.remove(pno);
        fileUtil.deleteFiles(oldFileNames);
        return Map.of("RESULT", "SUCCESS");
    }


}
















