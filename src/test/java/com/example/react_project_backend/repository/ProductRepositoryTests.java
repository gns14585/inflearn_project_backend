package com.example.react_project_backend.repository;

import com.example.react_project_backend.domain.Product;
import com.example.react_project_backend.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().pname("Test ").pdesc("Test Desc").price(1000).build();

            product.addImageString(UUID.randomUUID() + "_" + "IMAGE1.JPG");
            product.addImageString(UUID.randomUUID() + "_" + "IMAGE2.JPG");

            productRepository.save(product);
        }


    }

    @Test
    public void testRead2() {
        Long pno = 1L;
        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }

    // Test코드에선 삭제할땐 @Commit, @Transactional 어노테이션을 추가해야함
    @Commit
    @Transactional
    @Test
    public void testDelete() {
        Long pno = 2L;
        productRepository.updateToDelete(2L, true);
    }

    @Test
    public void testUpdate() {
        Product product = productRepository.selectOne(1L).get();
        product.changePrice(3000);

        // jpa가 arrayList를 관리하고있음, 그래서 현재 사용하고있는 컬렉션을 사용하여야함
        product.clearList();

        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE1.JPG");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE2.JPG");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE3.JPG");
        productRepository.save(product);
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        productRepository.searchList(pageRequestDTO);

    }




}
