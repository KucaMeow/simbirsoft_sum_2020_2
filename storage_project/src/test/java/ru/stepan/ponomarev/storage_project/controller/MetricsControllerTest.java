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
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.service.MetricTypeCrudServiceImpl;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class MetricsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MetricTypeCrudServiceImpl service;

    MetricType metricType1;
    MetricType metricType2;
    MetricType metricTypeSaved;

    @BeforeEach
    public void initTest() {
        metricType1 = MetricType.builder()
                .id(1L)
                .metric("metric1")
                .build();
        metricType2 = MetricType.builder()
                .id(2L)
                .metric("metric2")
                .build();
        metricTypeSaved = MetricType.builder()
                .id(3L)
                .metric("metric-test3")
                .build();
        given(service.showAllMetricTypes()).willReturn(Arrays.asList(metricType1, metricType2));
        given(service.showMetricTypeById(1)).willReturn(metricType1);
        given(service.showMetricTypeById(0)).willReturn(null);
        given(service.delete(1)).willReturn(true);
        given(service.delete(0)).willReturn(false);
        given(service.addOrUpdateMetricType(Mockito.any())).willReturn(metricTypeSaved);
    }

    @Test
    void getAllMetricTypesShouldReturnListWithMetrics() throws Exception {
        mockMvc.perform(get("/metric/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getMetricTypeWithId1ShouldReturnMetric() throws Exception {
        mockMvc.perform(get("/metric/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(metricType1)));
    }

    @Test
    void getMetricTypeWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/metric/get/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMetricTypeWithId1ShouldReturnNotFoundAfterDeleteById() throws Exception {
        mockMvc.perform(post("/metric/delete/1"))
                .andExpect(status().isOk());
        given(service.showMetricTypeById(1)).willReturn(null);
        mockMvc.perform(get("/metric/get/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMetricTypeByInvalidIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/metric/delete/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addingMetricTypeShouldReturnObjectWithId() throws Exception {
        MetricType metricType = MetricType.builder()
                .metric("metric-test3")
                .build();
        mockMvc.perform(post("/metric/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metricType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }
}
