package ru.stepan.ponomarev.storage_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ProductTypesCrudServiceTest {

    @Autowired
    ProductTypeCrudService service;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DtoMapper dtoMapper;

    @MockBean
    ProductTypeRepository productTypeRepository;

    List<ProductType> list;
    ProductType productType;
    ProductType productTypeSaved;
    ProductType productTypeDeleted;

    @BeforeEach
    void beforeTests () {
        productType = ProductType.builder()
                .id(1L)
                .name("test1")
                .build();
        productTypeDeleted = ProductType.builder()
                .id(null)
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
        assertEquals(ResponseEntity.ok(list.stream().map(dtoMapper::from).collect(Collectors.toList())),
                service.showAllProductsTypes());
    }

    @Test
    void showProductTypeByValidIdShouldReturnProductType () {
        assertEquals(ResponseEntity.ok(dtoMapper.from(productType)), service.showProductTypeById(1));
    }

    @Test
    void showProductTypeByInvalidIdShouldReturnResponseNotFound () {
        assertEquals(ResponseEntity.notFound().build(), service.showProductTypeById(0));
    }

    @Test
    void addOrUpdateProductTypeShouldReturnResponseOk() throws JsonProcessingException {
        ProductType productType = ProductType.builder()
                .name("test2")
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(productTypeSaved)),
                service.addOrUpdateProductType(dtoMapper.from(productType)));
    }

    @Test
    void deleteByValidIdShouldReturnResponseOk () {
        assertEquals(ResponseEntity.ok(dtoMapper.from(productTypeDeleted)), service.delete(1L));
    }

    @Test
    void deleteByInvalidIdShouldReturnResponseNotFound () {
        assertEquals(ResponseEntity.notFound().build(), service.delete(0L));
    }
}
