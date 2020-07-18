package ru.stepan.ponomarev.storage_project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MetricTypesCrudServiceTest {

    @Autowired
    MetricTypesCrudService service;

    @MockBean
    MetricTypeRepository metricTypeRepository;

    List<MetricType> list;
    MetricType metricType;
    MetricType metricTypeSaved;

    @BeforeEach
    void beforeTests () {
        metricType = MetricType.builder()
                .id(1L)
                .metric("test1")
                .build();
        metricTypeSaved = MetricType.builder()
                .id(2L)
                .metric("test2")
                .build();
        list = Collections.singletonList(metricType);
        given(metricTypeRepository.findAll()).willReturn(list);
        given(metricTypeRepository.findById(1L)).willReturn(Optional.of(metricType));
        given(metricTypeRepository.findById(0L)).willReturn(Optional.empty());
        given(metricTypeRepository.save(any())).willReturn(metricTypeSaved);
    }

    @Test
    void showAllMetricTypesShouldReturnList () {
        assertEquals(list, service.showAllMetricTypes());
    }

    @Test
    void showMetricTypeByValidIdShouldReturnMetricType () {
        assertEquals(metricType, service.showMetricTypeById(1));
    }

    @Test
    void showMetricTypeByInvalidIdShouldReturnNull () {
        assertNull(service.showMetricTypeById(0));
    }

    @Test
    void addOrUpdateMetricTypeShouldReturnMetricTypeWithId () {
        MetricType metricType = MetricType.builder().metric("test2").build();
        assertNotNull(service.addOrUpdateMetricType(metricType).getId());
    }

    @Test
    void deleteByValidIdShouldReturnTrue () {
        assertTrue(service.delete(1L));
    }

    @Test
    void deleteByInvalidIdShouldReturnFalse () {
        assertFalse(service.delete(0L));
    }
}
