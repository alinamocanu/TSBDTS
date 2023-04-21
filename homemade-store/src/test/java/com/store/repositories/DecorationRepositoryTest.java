package com.store.repositories;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.repository.DecorationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@Slf4j
public class DecorationRepositoryTest {
    @Autowired
    private DecorationRepository decorationRepository;

    @Test
    public void findProductsByCategory() {
        List<Decoration> products = decorationRepository.findAllByCategory(DecorationCategory.EASTER);
        assertTrue(products.size() >= 1);
        log.info("findByCategory ...");
        products.forEach(product -> log.info(product.getDecorationName()));
    }

    @Test
    public void findPage(){
        Pageable firstPage = PageRequest.of(0, 2);
        Page<Decoration> allProducts = decorationRepository.findAll(firstPage);
        assertTrue(allProducts.getNumberOfElements() == 2);
    }
}