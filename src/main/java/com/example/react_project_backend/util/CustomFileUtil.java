package com.example.react_project_backend.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() { // 폴더를 만들어주는 용도
        File tempFolder = new File(uploadPath);
        if (!tempFolder.exists()) { // tempFolder가 존재하지 않는다면 폴더생성
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("-----------------------------");
        log.info(uploadPath);
    }

    // --------------------------------- 파일 저장 로직 ---------------------------------
    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
        if (files == null || files.size() == 0) {
            return null;
        }
        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {
            // 똑같은 이름의 파일들이 있을경우 UUID 사용
            // 원본 파일 이름 : file.getOriginalFilename();
            String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(file.getInputStream(), savePath); // 원본 파일 업로드

                // 이미지인 경우에만 확인해서 썸네일 만듬
                // 썸네일을 만드는 이유는 기존 이미지를 업로드하면 원본 이미지파일이 업로드되는데 그렇게되면 용량이 높아짐
                // 용량을 낮추려고 썸네일을 이용함.
                String contentType = file.getContentType(); // Mime type
                // 원본파일은 s_ 가 없음
                if (contentType != null || contentType.startsWith("image")) { // 이미지파일 이라면(조건)
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);
                    // 썸네일 사이즈 조정
                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile());
                }

                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } // end for
        return uploadNames;
    }

    //--------------------------------- 파일 보기 로직 ---------------------------------
    public ResponseEntity<Resource> getFile(String fileName) {
        // File.separator : 경로를 설정할때 역슬래쉬기능 \
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if (!resource.isReadable()) {
            // File.separator : 경로를 설정할때 역슬래쉬기능 \
            // 이미지가 없을경우 기본이미지 보여주기
            resource = new FileSystemResource(uploadPath + File.separator + "default.png");
        }

        HttpHeaders headers = new HttpHeaders(); // 보내주는 데이터의 타입을 알려주는 용도
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    // --------------------------------- 파일 삭제 로직 ---------------------------------
    public void deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.isEmpty()) {
            return;
        }

        fileNames.forEach(fileName -> {
            String thumbnailFileName = "s_" + fileName; // 썸네일 변수

            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName); // 썸네일
            Path filePath = Paths.get(uploadPath, fileName); // 원본파일

            try {
                // deleteIfExists : 만약에 해당 파일이 있으면 삭제요청
                Files.deleteIfExists(filePath); // 원본파일 삭제
                Files.deleteIfExists(thumbnailPath); // 썸네일 삭제
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }














}
