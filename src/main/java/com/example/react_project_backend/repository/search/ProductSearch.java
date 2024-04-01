package com.example.react_project_backend.repository.search;

import com.example.react_project_backend.dto.PageRequestDTO;
import com.example.react_project_backend.dto.PageResponseDTO;
import com.example.react_project_backend.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList (PageRequestDTO pageRequestDTO);

}
