package ru.stepan.ponomarev.storage_project.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DtoMapperTest {

    @Autowired
    DtoMapper dtoMapper;

    @MockBean
    MetricTypeRepository metricTypeRepository;
    @MockBean
    ProductTypeRepository productTypeRepository;

    static Product product;
    static ProductDto productDto;
    static MetricType metricType;
    static ProductType productType;

    @BeforeAll
    static void init() {
        metricType = new MetricType(1L, "m");
        productType = new ProductType(1L, "p_t");
        product = Product.builder()
                .quantity(1)
                .name("p")
                .productType(productType)
                .metricType(metricType)
                .cost(1)
                .id(1L)
                .build();
        productDto = ProductDto.builder()
                .quantity(1)
                .name("p")
                .productTypeId(1L)
                .metricTypeId(1L)
                .cost(1)
                .id(1L)
                .build();
    }

    @Test
    void testProductToProductDto () {
        assertEquals(productDto, dtoMapper.from(product));
    }

    @Test
    void testProductDtoToProduct () {
        given(metricTypeRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(metricType));
        given(productTypeRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(productType));
        assertEquals(productDto, dtoMapper.from(product));
    }

}
