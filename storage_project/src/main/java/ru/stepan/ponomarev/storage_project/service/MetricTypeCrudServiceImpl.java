package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.MetricTypeDto;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses MetricTypeRepository bean
 */
@Service
public class MetricTypeCrudServiceImpl implements MetricTypeCrudService {

    private final MetricTypeRepository metricTypeRepository;
    private final DtoMapper dtoMapper;

    public MetricTypeCrudServiceImpl(MetricTypeRepository metricTypeRepository, DtoMapper dtoMapper) {
        this.metricTypeRepository = metricTypeRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    public ResponseEntity<List<MetricTypeDto>> showAllMetricTypes() {
        return ResponseEntity.ok(metricTypeRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ResponseEntity<MetricTypeDto> showMetricTypeById(long id) {
        Optional<MetricType> product = metricTypeRepository.findById(id);
        return product.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ResponseEntity<MetricTypeDto> addOrUpdateMetricType(MetricTypeDto metricType) {
        return ResponseEntity.ok(dtoMapper.from(metricTypeRepository.save(dtoMapper.from(metricType))));
    }

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return String message. Response ok and "Deleted metric with id *id*" or Response NOT_FOUND and "Can't find metric type by id"
     */
    public ResponseEntity<MetricTypeDto> delete (long id) {
        Optional<MetricType> metricType = metricTypeRepository.findById(id);
        if(metricType.isPresent()) {
            metricTypeRepository.delete(metricType.get());
            metricType.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(metricType.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
