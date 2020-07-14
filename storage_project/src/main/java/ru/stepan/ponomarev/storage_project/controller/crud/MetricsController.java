package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.MetricType;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class MetricsController {

    @Autowired
    MetricTypeRepository metricTypeRepository;

    @GetMapping("/metric/get/all")
    public ResponseEntity<List<MetricType>> showAllMetricTypes() {
        return ResponseEntity.ok(metricTypeRepository.findAll());
    }

    @GetMapping("/metric/get/{id}")
    public ResponseEntity<MetricType> showMetricTypeById(@PathVariable long id) {
        Optional<MetricType> product = metricTypeRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("metric/save")
    public ResponseEntity<MetricType> addOrUpdateMetricType(@RequestBody MetricType metricType) {
        return ResponseEntity.ok(metricTypeRepository.save(metricType));
    }

    @PostMapping("metric/update/{id}")
    public ResponseEntity<MetricType> updateWithId(@RequestBody MetricType metricType, @PathVariable long id) {
        metricType.setId(id);
        return ResponseEntity.ok(metricTypeRepository.save(metricType));
    }

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
