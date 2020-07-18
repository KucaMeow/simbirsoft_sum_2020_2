package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.service.MetricTypesCrudService;

import java.util.List;

/**
 * Controller for metric types crud operations
 */
@RestController
public class MetricsController {

    final
    MetricTypesCrudService service;

    public MetricsController(MetricTypesCrudService service) {
        this.service = service;
    }

    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    @ApiOperation(value = "Show all metric types from repository",
            produces = "Response OK with list of MetricType objects")
    @GetMapping("/metric/get/all")
    public ResponseEntity<List<MetricType>> showAllMetricTypes() {
        return ResponseEntity.ok(service.showAllMetricTypes());
    }

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show metric type by it's id from repository",
            produces = "Response OK with MetricType object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/metric/get/{id}")
    public ResponseEntity<MetricType> showMetricTypeById(@PathVariable @ApiParam("id of metric type") long id) {
        MetricType metricType = service.showMetricTypeById(id);
        if(metricType != null) {
            return ResponseEntity.ok(metricType);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new metric type from request body",
            produces = "Response OK with updated object")
    @PostMapping("/metric/save")
    public ResponseEntity<MetricType> addOrUpdateMetricType(@RequestBody @ApiParam("MetricType object from request body") MetricType metricType) {
        return ResponseEntity.ok(service.addOrUpdateMetricType(metricType));
    }

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return String message. Response ok and "Deleted metric with id *id*" or Response NOT_FOUND and "Can't find metric type by id"
     */
    @ApiOperation(value = "Delete metric type from repository",
            produces = "Response ok and \"Deleted metric with id *id*\", Response NOT_FOUND and \"Can't find metric type by id\"")
    @PostMapping("metric/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable @ApiParam("id of MetricType to delete") long id) {
        if(service.delete(id)) {
            return ResponseEntity.ok("Deleted product with id " + id);
        }
        return new ResponseEntity<>("Can't find metric type by id", HttpStatus.NOT_FOUND);
    }
}
