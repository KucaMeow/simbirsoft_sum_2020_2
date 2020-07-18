package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.model.MetricType;

import java.util.List;

public interface MetricTypesCrudService {
    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    List<MetricType> showAllMetricTypes();

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, or Response NOT_FOUND if it isn't found
     */
    MetricType showMetricTypeById(long id);

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    MetricType addOrUpdateMetricType(MetricType metricType);

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return String message. Response ok and "Deleted metric with id *id*" or Response NOT_FOUND and "Can't find metric type by id"
     */
    boolean delete (long id);
}
