package ru.stepan.ponomarev.storage_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ProductsCrudServiceTest {

    @Autowired
    ProductCrudService service;
    @Autowired
    DtoMapper dtoMapper;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductsRepository productsRepository;

    List<Product> list;
    List<ProductDto> listDtos;
    Product product;
    ProductDto productDto;
    Product productSaved;
    MetricType metricType;
    ProductType productType;
    ProductDto productDtoDeleted;

    @BeforeEach
    void beforeTests () {
        metricType = MetricType.builder().id(1L).metric("metrict").build();
        productType = ProductType.builder().id(1L).name("prod_type").build();
        product = Product.builder()
                .id(1L)
                .cost(10)
                .metricType(metricType)
                .productType(productType)
                .name("test1")
                .quantity(10)
                .build();
        productSaved = Product.builder()
                .id(2L)
                .cost(10)
                .metricType(metricType)
                .productType(productType)
                .name("test2")
                .quantity(10)
                .build();
        productDto = ProductDto.builder()
                .id(1L)
                .cost(10)
                .metricTypeId(1L)
                .productTypeId(1L)
                .name("test1")
                .quantity(10)
                .build();
        productDtoDeleted = ProductDto.builder()
                .name("test1")
                .quantity(10)
                .id(null)
                .cost(10)
                .metricTypeId(1L)
                .productTypeId(1L)
                .build();
        list = Collections.singletonList(product);
        listDtos = Collections.singletonList(productDto);
        given(productsRepository.findAll()).willReturn(list);
        given(productsRepository.findById(1L)).willReturn(Optional.of(product));
        given(productsRepository.findById(0L)).willReturn(Optional.empty());
        given(productsRepository.save(any())).willReturn(productSaved);
    }

    @Test
    void showAllProductsShouldReturnListOfProductDtos () {
        assertEquals(ResponseEntity.ok(listDtos), service.showAllProducts());
    }

    @Test
    void showProductByValidIdShouldReturnProductDto () {
        assertEquals(ResponseEntity.ok(productDto), service.showProductById(1L));
    }

    @Test
    void showProductByInvalidIdShouldReturnNull () {
        assertEquals(ResponseEntity.notFound().build(), service.showProductById(0L));
    }

    @Test
    void addOrUpdateProduct () {
        ProductDto product = ProductDto.builder()
                .cost(10)
                .metricTypeId(1L)
                .productTypeId(1L)
                .name("test2")
                .quantity(10)
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(productSaved)), service.addOrUpdateProduct(product));
    }

    @Test
    void deleteByValidIdShouldReturnResponseOk () {
        Assertions.assertEquals(ResponseEntity.ok(productDtoDeleted), service.delete(1L));
    }

    @Test
    void deleteByInvalidIdShouldReturnResponseNotFound () {
        Assertions.assertEquals(ResponseEntity.notFound().build(), service.delete(0L));
    }
}
