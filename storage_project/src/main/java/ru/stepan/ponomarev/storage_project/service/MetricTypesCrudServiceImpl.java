package ru.stepan.ponomarev.storage_project.service;

import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Uses MetricTypeRepository bean
 */
@Service
public class MetricTypesCrudServiceImpl implements MetricTypesCrudService {

    final
    MetricTypeRepository metricTypeRepository;

    public MetricTypesCrudServiceImpl(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    public List<MetricType> showAllMetricTypes() {
        return metricTypeRepository.findAll();
    }

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, or Response NOT_FOUND if it isn't found
     */
    public MetricType showMetricTypeById(long id) {
        Optional<MetricType> product = metricTypeRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public MetricType addOrUpdateMetricType(MetricType metricType) {
        return metricTypeRepository.save(metricType);
    }

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return String message. Response ok and "Deleted metric with id *id*" or Response NOT_FOUND and "Can't find metric type by id"
     */
    public boolean delete (long id) {
        Optional<MetricType> metricType = metricTypeRepository.findById(id);
        if(metricType.isPresent()) {
            metricTypeRepository.delete(metricType.get());
            return true;
        }
        return false;
    }
}
