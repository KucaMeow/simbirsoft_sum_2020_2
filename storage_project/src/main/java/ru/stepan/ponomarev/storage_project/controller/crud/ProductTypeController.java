package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller for product type type crud operations
 */
@RestController
public class ProductTypeController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    @GetMapping("/product-type/get/all")
    public ResponseEntity<List<ProductType>> showAllProductsTypes() {
        return ResponseEntity.ok(productTypeRepository.findAll());
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    @GetMapping("/product-type/get/{id}")
    public ResponseEntity<ProductType> showProductTypeById(@PathVariable long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @PostMapping("product-type/save")
    public ResponseEntity<ProductType> addOrUpdateProductType(@RequestBody ProductType productType) {
        return ResponseEntity.ok(productTypeRepository.save(productType));
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return String message. Response ok and "Deleted product types with id *id*" or Response NOT_FOUND and "Can't find product types by id"
     */
    @PostMapping("product-type/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()) {
            productTypeRepository.delete(productType.get());
            return ResponseEntity.ok("Deleted product types with id " + id);
        }
        return new ResponseEntity<>("Can't find product types type by id", HttpStatus.NOT_FOUND);
    }
}
