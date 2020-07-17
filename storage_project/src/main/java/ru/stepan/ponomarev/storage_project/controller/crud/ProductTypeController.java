package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;
import ru.stepan.ponomarev.storage_project.service.ProductTypesCrudService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for product type type crud operations
 */
@RestController
public class ProductTypeController {

    final
    ProductTypesCrudService service;

    public ProductTypeController(ProductTypesCrudService service) {
        this.service = service;
    }

    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    @ApiOperation(value = "Show all product types from repository",
            produces = "Response OK with list of ProductType objects")
    @GetMapping("/product-type/get/all")
    public ResponseEntity<List<ProductType>> showAllProductsTypes() {
        return ResponseEntity.ok(service.showAllProductsTypes());
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show product types by it's id from repository",
            produces = "Response OK with ProductType object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/product-type/get/{id}")
    public ResponseEntity<ProductType> showProductTypeById(@PathVariable @ApiParam("id of product types") long id) {
        ProductType productType = service.showProductTypeById(id);
        if(productType != null) {
            return ResponseEntity.ok(productType);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new product types from request body",
            produces = "Response OK with updated object")
    @PostMapping("product-type/save")
    public ResponseEntity<ProductType> addOrUpdateProductType(
            @RequestBody @ApiParam("ProductType object from request body") ProductType productType) {
        return ResponseEntity.ok(service.addOrUpdateProductType(productType));
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return String message. Response ok and "Deleted product types with id *id*" or Response NOT_FOUND and "Can't find product types by id"
     */
    @ApiOperation(value = "Delete product types from repository",
            produces = "Response ok and \"Deleted product types with id *id*\", Response NOT_FOUND and \"Can't find product types by id\"")
    @PostMapping("product-type/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable @ApiParam("id of ProductType to delete") long id) {
        if(service.delete(id)) {
            return ResponseEntity.ok("Deleted product types with id " + id);
        }
        return new ResponseEntity<>("Can't find product types type by id", HttpStatus.NOT_FOUND);
    }
}
