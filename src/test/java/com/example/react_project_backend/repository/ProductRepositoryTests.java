package com.example.react_project_backend.repository;

import com.example.react_project_backend.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        Product product = Product.builder().pname("Test ").pdesc("Test Desc").price(1000).build();

        product.addImageString(UUID.randomUUID() + "_" + "IMAGE1.JPG");
        product.addImageString(UUID.randomUUID() + "_" + "IMAGE2.JPG");

        productRepository.save(product);
    }

    @Test
    public void testRead2() {
        Long pno = 1L;
        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }

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
        product.clearList();
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE1.JPG");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE2.JPG");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE3.JPG");
        productRepository.save(product);
    }


}
