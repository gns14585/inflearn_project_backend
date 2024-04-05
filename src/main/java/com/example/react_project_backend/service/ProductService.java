package com.example.react_project_backend.service;

import com.example.react_project_backend.dto.PageRequestDTO;
import com.example.react_project_backend.dto.PageResponseDTO;
import com.example.react_project_backend.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long pno);

}
