package ru.stepan.ponomarev.storage_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.service.DtoMapper;
import ru.stepan.ponomarev.storage_project.service.ProductCrudServiceImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ProductsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DtoMapper dtoMapper;

    @MockBean
    ProductCrudServiceImpl service;

    Product product1;
    Product product2;
    Product productToSave;
    MetricType metricType;
    ProductType productType;
    ProductDto productDtoDeleted;

    @BeforeEach
    public void initTest() {
        metricType = MetricType.builder()
                .id(1L)
                .metric("metric")
                .build();
        productType = ProductType.builder()
                .id(1L)
                .name("prod_type")
                .build();
        product1 = Product.builder()
                .name("product1")
                .quantity(10)
                .id(1L)
                .cost(10)
                .metricType(metricType)
                .productType(productType)
                .build();
        product2 = Product.builder()
                .name("product2")
                .quantity(10)
                .id(2L)
                .cost(10)
                .metricType(metricType)
                .productType(productType)
                .build();
        productToSave = Product.builder()
                .name("productToSave")
                .quantity(10)
                .id(3L)
                .cost(10)
                .metricType(metricType)
                .productType(productType)
                .build();
        productDtoDeleted = ProductDto.builder()
                .name("product1")
                .quantity(10)
                .id(null)
                .cost(10)
                .metricTypeId(1L)
                .productTypeId(1L)
                .build();
        given(service.showAllProducts()).willReturn(
                ResponseEntity.ok(Arrays.asList(dtoMapper.from(product1), dtoMapper.from(product2))));
        given(service.showProductById(1L)).willReturn(ResponseEntity.ok(dtoMapper.from(product1)));
        given(service.showProductById(0L)).willReturn(ResponseEntity.notFound().build());
        given(service.delete(1L)).willReturn(ResponseEntity.ok(productDtoDeleted));
        given(service.delete(0L)).willReturn(ResponseEntity.notFound().build());
        given(service.addOrUpdateProduct(any())).willReturn(ResponseEntity.ok(dtoMapper.from(productToSave)));

    }

    @Test
    void getAllProductsShouldReturnListWithProducts() throws Exception {
        mockMvc.perform(get("/products/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductWithId1ShouldReturnProductType() throws Exception {
        mockMvc.perform(get("/products/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dtoMapper.from(product1))));
    }

    @Test
    void getProductWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/products/get/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductByValidIdShouldReturnObjectWithNullId() throws Exception {
        mockMvc.perform(post("/products/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", nullValue()));
    }

    @Test
    void deleteProductWithInvalidIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/products/delete/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addingMetricTypeShouldReturnObjectWithId() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("productToSave")
                .quantity(10)
                .cost(10)
                .metricTypeId(1L)
                .productTypeId(1L)
                .build();
        mockMvc.perform(post("/products/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }
}
