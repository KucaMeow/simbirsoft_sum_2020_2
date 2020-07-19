package ru.stepan.ponomarev.storage_project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ProductTypesCrudServiceTest {

    @Autowired
    ProductTypeCrudService service;

    @MockBean
    ProductTypeRepository productTypeRepository;

    List<ProductType> list;
    ProductType productType;
    ProductType productTypeSaved;

    @BeforeEach
    void beforeTests () {
        productType = ProductType.builder()
                .id(1L)
                .name("test1")
                .build();
        productTypeSaved = ProductType.builder()
                .id(2L)
                .name("test2")
                .build();
        list = Collections.singletonList(productType);
        given(productTypeRepository.findAll()).willReturn(list);
        given(productTypeRepository.findById(1L)).willReturn(Optional.of(productType));
        given(productTypeRepository.findById(0L)).willReturn(Optional.empty());
        given(productTypeRepository.save(any())).willReturn(productTypeSaved);
    }

    @Test
    void showAllProductsTypesShouldReturnList () {
        assertEquals(list, service.showAllProductsTypes());
    }

    @Test
    void showProductTypeByValidIdShouldReturnProductType () {
        assertEquals(productType, service.showProductTypeById(1));
    }

    @Test
    void showProductTypeByInvalidIdShouldReturnNull () {
        assertNull(service.showProductTypeById(0));
    }

    @Test
    void addOrUpdateProductTypeShouldReturnProductTypeWithId () {
        ProductType metricType = ProductType.builder()
                .name("test2")
                .build();
        assertNotNull(service.addOrUpdateProductType(metricType).getId());
    }

    @Test
    void deleteByValidIdShouldReturnTrue () {
        Assertions.assertTrue(service.delete(1L));
    }

    @Test
    void deleteByInvalidIdShouldReturnFalse () {
        Assertions.assertFalse(service.delete(0L));
    }
}
