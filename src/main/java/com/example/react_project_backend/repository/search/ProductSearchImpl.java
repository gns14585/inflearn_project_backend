package com.example.react_project_backend.repository.search;

import com.example.react_project_backend.domain.Product;
import com.example.react_project_backend.domain.QProduct;
import com.example.react_project_backend.domain.QProductImage;
import com.example.react_project_backend.dto.PageRequestDTO;
import com.example.react_project_backend.dto.PageResponseDTO;
import com.example.react_project_backend.dto.ProductDTO;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {

        log.info("---------------------searchList-------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);

//        query.leftJoin()

        return null;
    }


}
