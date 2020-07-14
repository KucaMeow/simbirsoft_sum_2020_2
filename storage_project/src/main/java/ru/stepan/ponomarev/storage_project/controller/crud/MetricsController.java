package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller for metric types crud operations
 */
@RestController
public class MetricsController {

    @Autowired
    MetricTypeRepository metricTypeRepository;

    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    @GetMapping("/metric/get/all")
    public ResponseEntity<List<MetricType>> showAllMetricTypes() {
        return ResponseEntity.ok(metricTypeRepository.findAll());
    }

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, or Response NOT_FOUND if it isn't found
     */
    @GetMapping("/metric/get/{id}")
    public ResponseEntity<MetricType> showMetricTypeById(@PathVariable long id) {
        Optional<MetricType> product = metricTypeRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @PostMapping("metric/save")
    public ResponseEntity<MetricType> addOrUpdateMetricType(@RequestBody MetricType metricType) {
        return ResponseEntity.ok(metricTypeRepository.save(metricType));
    }

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return String message. Response ok and "Deleted metric with id *id*" or Response NOT_FOUND and "Can't find metric type by id"
     */
    @PostMapping("metric/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable long id) {
        Optional<MetricType> metricType = metricTypeRepository.findById(id);
        if(metricType.isPresent()) {
            metricTypeRepository.delete(metricType.get());
            return ResponseEntity.ok("Deleted product with id " + id);
        }
        return new ResponseEntity<>("Can't find metric type by id", HttpStatus.NOT_FOUND);
    }
}
