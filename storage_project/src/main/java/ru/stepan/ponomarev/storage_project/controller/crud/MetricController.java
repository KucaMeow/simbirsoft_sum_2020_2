package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.MetricTypeDto;
import ru.stepan.ponomarev.storage_project.service.MetricTypeCrudService;

import java.util.List;

/**
 * Controller for metric types crud operations
 */
@RestController
public class MetricController {

    private final MetricTypeCrudService service;

    public MetricController(MetricTypeCrudService service) {
        this.service = service;
    }

    /**
     * Show all metric types from repository
     * @return Response OK with list of MetricType objects
     */
    @ApiOperation(value = "Show all metric types from repository",
            produces = "Response OK with list of MetricType objects")
    @GetMapping("/metric")
    public ResponseEntity<List<MetricTypeDto>> showAllMetricTypes() {
        return service.showAllMetricTypes();
    }

    /**
     * Show metric type by it's id from repository
     * @param id id of metric type
     * @return Response OK with MetricType object if it's found, Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show metric type by it's id from repository",
            produces = "Response OK with MetricType object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/metric/{id}")
    public ResponseEntity<MetricTypeDto> showMetricTypeById(@PathVariable @ApiParam("id of metric type") long id) {
        return service.showMetricTypeById(id);
    }

    /**
     * Save new metric type from request body
     * @param metricType MetricType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new metric type from request body",
            produces = "Response OK with updated object")
    @PutMapping("/metric")
    public ResponseEntity<MetricTypeDto> addOrUpdateMetricType(
            @RequestBody @ApiParam("MetricType object from request body") MetricTypeDto metricType) {
        return service.addOrUpdateMetricType(metricType);
    }

    /**
     * Delete metric type from repository
     * @param id id of MetricType to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    @ApiOperation(value = "Delete metric type from repository",
            produces = "Response NOT_FOUND if there's no object with this id, Response OK with deleted object with null id")
    @DeleteMapping("metric/{id}")
    public ResponseEntity<MetricTypeDto> delete (@PathVariable @ApiParam("id of MetricType to delete") long id) {
        return service.delete(id);
    }
}
