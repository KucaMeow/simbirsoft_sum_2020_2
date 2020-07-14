package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductTypeController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @GetMapping("/product-type/get/all")
    public ResponseEntity<List<ProductType>> showAllProductsTypes() {
        return ResponseEntity.ok(productTypeRepository.findAll());
    }

    @GetMapping("/product-type/get/{id}")
    public ResponseEntity<ProductType> showProductTypeById(@PathVariable long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("product-type/save")
    public ResponseEntity<ProductType> addOrUpdateProductType(@RequestBody ProductType productType) {
        return ResponseEntity.ok(productTypeRepository.save(productType));
    }

    @PostMapping("product-type/update/{id}")
    public ResponseEntity<ProductType> updateWithId(@RequestBody ProductType productType, @PathVariable long id) {
        productType.setId(id);
        return ResponseEntity.ok(productTypeRepository.save(productType));
    }

    @PostMapping("product-type/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()) {
            productTypeRepository.delete(productType.get());
            return ResponseEntity.ok("Deleted product with id " + id);
        }
        return new ResponseEntity<>("Can't find product type by id", HttpStatus.NOT_FOUND);
    }
}
