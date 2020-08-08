package ru.stepan.ponomarev.storage_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepan.ponomarev.storage_project.dto.MetricTypeDto;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.security.config.WebSecurityConfig;
import ru.stepan.ponomarev.storage_project.service.MetricTypeCrudServiceImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    MetricTypeDto metricType1;
    MetricTypeDto metricType2;
    MetricTypeDto metricTypeSaved;
    MetricTypeDto metricTypeDeleted;

    @BeforeEach
    public void initTest() {
        metricType1 = MetricTypeDto.builder()
                .id(1L)
                .metric("metric1")
                .build();
        metricTypeDeleted = MetricTypeDto.builder()
                .id(null)
                .metric("metric1")
                .build();
        metricType2 = MetricTypeDto.builder()
                .id(2L)
                .metric("metric2")
                .build();
        metricTypeSaved = MetricTypeDto.builder()
                .id(3L)
                .metric("metric-test3")
                .build();
        given(service.showAllMetricTypes()).willReturn(ResponseEntity.ok(Arrays.asList(metricType1, metricType2)));
        given(service.showMetricTypeById(1)).willReturn(ResponseEntity.ok(metricType1));
        given(service.showMetricTypeById(0)).willReturn(ResponseEntity.notFound().build());
        given(service.delete(1)).willReturn(ResponseEntity.ok(metricTypeDeleted));
        given(service.delete(0)).willReturn(ResponseEntity.notFound().build());
        given(service.addOrUpdateMetricType(Mockito.any())).willReturn(ResponseEntity.ok(metricTypeSaved));
    }

    @Test
    void getAllMetricTypesShouldReturnListWithMetrics() throws Exception {
        mockMvc.perform(get("/metric"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getMetricTypeWithId1ShouldReturnMetric() throws Exception {
        mockMvc.perform(get("/metric/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(metricType1)));
    }

    @Test
    void getMetricTypeWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/metric/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMetricTypeByValidIdShouldReturnObjectWithNullId() throws Exception {
        mockMvc.perform(delete("/metric/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", nullValue()));
    }

    @Test
    void deleteMetricTypeByInvalidIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/metric/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addingMetricTypeShouldReturnObjectWithId() throws Exception {
        MetricType metricType = MetricType.builder()
                .metric("metric-test3")
                .build();
        mockMvc.perform(put("/metric")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metricType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }
}
