package ru.stepan.ponomarev.storage_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.service.ProductTypeCrudService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ProductTypeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductTypeCrudService service;

    ProductType productType1;
    ProductType productType2;
    ProductType productTypeSaved;

    @BeforeEach
    public void initTest() {
        productType1 = ProductType.builder()
                .id(1L)
                .name("product-type1")
                .build();
        productType2 = ProductType.builder()
                .id(2L)
                .name("product-type2")
                .build();
        productTypeSaved = ProductType.builder()
                .id(3L)
                .name("product-type-test3")
                .build();
        given(service.showAllProductsTypes()).willReturn(Arrays.asList(productType1, productType2));
        given(service.showProductTypeById(1)).willReturn(productType1);
        given(service.showProductTypeById(0)).willReturn(null);
        given(service.delete(1)).willReturn(true);
        given(service.delete(0)).willReturn(false);
        given(service.addOrUpdateProductType(Mockito.any())).willReturn(productTypeSaved);
    }

    @Test
    void getAllProductTypesShouldReturnListWithProductTypes() throws Exception {
        mockMvc.perform(get("/product-type/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductTypeWithId1ShouldReturnProductType() throws Exception {
        mockMvc.perform(get("/product-type/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productType1)));
    }

    @Test
    void getProductTypeWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/product-type/get/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductTypeWithId1ShouldReturnNotFoundAfterDeleteById() throws Exception {
        mockMvc.perform(post("/product-type/delete/1"))
                .andExpect(status().isOk());
        given(service.showProductTypeById(1)).willReturn(null);
        mockMvc.perform(get("/product-type/get/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductTypeByInvalidIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/product-type/delete/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addingProductTypeShouldReturnObjectWithId() throws Exception {
        ProductType productType = ProductType.builder()
                .name("product-type-test3")
                .build();
        mockMvc.perform(post("/product-type/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }
}
